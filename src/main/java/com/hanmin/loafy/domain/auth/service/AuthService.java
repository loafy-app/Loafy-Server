package com.hanmin.loafy.domain.auth.service;

import com.hanmin.loafy.domain.auth.dto.request.LoginRequest;
import com.hanmin.loafy.domain.auth.dto.response.LoginResponse;
import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.domain.member.repository.MemberRepository;
import com.hanmin.loafy.global.code.AuthErrorCode;
import com.hanmin.loafy.global.exception.AuthException;
import com.hanmin.loafy.global.security.auth.CustomUserDetails;
import com.hanmin.loafy.global.security.auth.CustomUserDetailsService;
import com.hanmin.loafy.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;


    // 로그인
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("[ AuthService ]: 로그인 실패 - 이메일을 찾을 수 없습니다.");
                    return new AuthException(AuthErrorCode.AUTH_UNAUTHORIZED_401);
                });
        if (!passwordEncoder.matches(request.password(), member.getPassword())){
            log.warn(" [ AuthService ]: 로그인 실패 - 비밀번호가 일치하지 않습니다.");
            throw new AuthException(AuthErrorCode.AUTH_UNAUTHORIZED_401);
        }
        String accessToken = jwtUtil.createJwtAccessToken(
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(member.getEmail()));
        String refreshToken = jwtUtil.createJwtRefreshToken(
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(member.getEmail()));
        return new LoginResponse(accessToken, refreshToken);
    }

}
