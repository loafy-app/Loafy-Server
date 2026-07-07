package com.hanmin.loafy.global.security.jwt;

import com.hanmin.loafy.domain.member.converter.MemberConverter;
import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.global.security.auth.CustomUserDetails;
import com.hanmin.loafy.global.security.auth.Roles;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Long accessExpMs;
    private final Long refreshExpMs;
    private final TokenRepository tokenRepository;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token.access-expiration-time}") Long accessExpirationTime,
            @Value("${jwt.token.refresh-expiration-time}") Long refreshExpirationTime,
            TokenRepository tokenRepo
    ){
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        accessExpMs = accessExpirationTime;
        refreshExpMs = refreshExpirationTime;
        tokenRepository = tokenRepo;
    }

    // SignatureException: jwt의 signature값의 불일치로 발생하는 예외
    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Roles getRoles(String token) throws SignatureException {
        String roleStr = Jwts.parser()
                .verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
        try {
            return Roles.valueOf(roleStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new SignatureException("유효하지 않은 Role값입니다.");
        }
    }

    public String tokenProvider(CustomUserDetails customUserDetails, Instant expiration) {
        Instant issuedAt = Instant.now();
        String authorities = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return Jwts.builder()
                .header().add("typ", "JWT")
                .and()
                .subject(customUserDetails.getUsername())
                .claim("role", authorities)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public String createJwtAccessToken(CustomUserDetails customUserDetails) {
        Instant expiration = Instant.now().plusMillis(accessExpMs);
        return tokenProvider(customUserDetails, expiration);
    }

    public String createJwtRefreshToken(CustomUserDetails customUserDetails) {
        Instant expiration = Instant.now().plusMillis(refreshExpMs);
        String refreshToken = tokenProvider(customUserDetails, expiration);
        tokenRepository.save(Token.builder()
                .email(customUserDetails.getUsername())
                .refreshToken(refreshToken)
                .build()
        );
        return refreshToken;
    }

    public JwtDTO reissueToken(String refreshToken) throws SignatureException {
        CustomUserDetails customUserDetails = new CustomUserDetails(
                getEmail(refreshToken),
                null,
                getRoles(refreshToken)
        );
        log.info("[ JwtUtil ]: 새로운 토큰을 재발급합니다.");
        return new JwtDTO(
                createJwtAccessToken(customUserDetails),
                createJwtRefreshToken(customUserDetails)
        );
    }

    public String resolveAccessToken(HttpServletRequest request) {
        log.info("[ JwtUtil ]: 헤더에서 토큰을 추출합니다.");
        String tokenFromHeader = request.getHeader("Authorization");
        if (tokenFromHeader == null || !tokenFromHeader.startsWith("Bearer ")) {
            log.warn("[ JwtUtil ]: 헤더에 토큰이 존재하지 않습니다.");
            return null;
        }
        log.info("[ JwtUtil ]: 헤더에 토큰이 존재합니다.");
        return tokenFromHeader.split(" ")[1];
    }

    public void validateToken(String token) {
        log.info("[ JwtUtil ]: 토큰의 유효성을 검증합니다.");
        try {
            long seconds = 3 * 60;
            Jwts.parser()
                    .clockSkewSeconds(seconds)
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            log.warn("[ JwtUtil ]: 만료된 JWT 토큰입니다.");
            throw new ExpiredJwtException(null, null, "만료된 JWT 토큰입니다.");
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new SecurityException("잘못된 토큰입니다.");
        }
    }

}
