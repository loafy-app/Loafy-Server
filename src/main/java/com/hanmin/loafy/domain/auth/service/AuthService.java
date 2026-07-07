package com.hanmin.loafy.domain.auth.service;

import com.hanmin.loafy.domain.auth.dto.request.LoginRequest;
import com.hanmin.loafy.domain.auth.dto.response.LoginResponse;
import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.domain.member.repository.MemberRepository;
import com.hanmin.loafy.global.code.AuthErrorCode;
import com.hanmin.loafy.global.exception.AuthException;
import com.hanmin.loafy.global.exception.CustomException;
import com.hanmin.loafy.global.security.auth.CustomUserDetails;
import com.hanmin.loafy.global.security.auth.CustomUserDetailsService;
import com.hanmin.loafy.global.security.jwt.JwtDTO;
import com.hanmin.loafy.global.security.jwt.JwtUtil;
import com.hanmin.loafy.global.security.jwt.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SignatureException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenRepository tokenRepository;

    // 로그인
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("[ AuthService ]: 로그인 실패 - 이메일을 찾을 수 없습니다.");
                    return new AuthException(AuthErrorCode.INCORRECT_EMAIL_PASSWORD);
                });
        if (!passwordEncoder.matches(request.password(), member.getPassword())){
            log.warn(" [ AuthService ]: 로그인 실패 - 비밀번호가 일치하지 않습니다.");
            throw new AuthException(AuthErrorCode.INCORRECT_EMAIL_PASSWORD);
        }
        String accessToken = jwtUtil.createJwtAccessToken(
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(member.getEmail()));
        log.info("[ AuthService ]: AccessToken이 생성되었습니다.");
        String refreshToken = jwtUtil.createJwtRefreshToken(
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(member.getEmail()));
        log.info("[ AuthService ]: RefreshToken이 생성되었습니다.");
        return new LoginResponse(accessToken, refreshToken);
    }

    // 토큰 재발급
    public JwtDTO reissue(JwtDTO request) throws SignatureException {
        String refreshToken = request.jwtRefreshToken();
        log.info("[ AuthService ]: 사용자 RefreshToken 추출 완료");
        if (tokenRepository.existsByEmail(jwtUtil.getEmail(refreshToken))) {
            return jwtUtil.reissueToken(refreshToken);
        }
        else {
            log.warn("[ AuthService ]: RefreshToken을 DB에서 찾을 수 없습니다.");
            throw new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    // 로그아웃
    public void logout(String email) {
        if (!tokenRepository.existsByEmail(email)) {
            log.warn("[ AuthService ]: 사용자의 RefreshToken을 DB에서 찾을 수 없습니다.");
            throw new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }
        else {
            tokenRepository.deleteTokenByEmail(email);
            log.info("[ AuthService ]: 사용자의 RefreshToken이 삭제되었습니다.");
        }
    }

}
