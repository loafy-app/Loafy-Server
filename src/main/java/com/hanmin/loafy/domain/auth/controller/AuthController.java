package com.hanmin.loafy.domain.auth.controller;

import com.hanmin.loafy.domain.auth.dto.request.LoginRequest;
import com.hanmin.loafy.domain.auth.dto.response.LoginResponse;
import com.hanmin.loafy.domain.auth.service.AuthService;
import com.hanmin.loafy.global.CustomResponse;
import com.hanmin.loafy.global.security.auth.CustomUserDetails;
import com.hanmin.loafy.global.security.jwt.JwtDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API", description = "사용자 인증/인가 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "이메일 로그인 API", description = "이메일 로그인 API 입니다.")
    @PostMapping("/login")
    public CustomResponse<?> emailLogin(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        log.info("[ AuthController ]: 로그인 성공");
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "토큰 재발급 API", description = "토큰 재발급 API 입니다.")
    @PostMapping("/reissue")
    public CustomResponse<?> reissueToken(@RequestBody JwtDTO request) throws SignatureException {
        JwtDTO response = authService.reissue(request);
        log.info("[ AuthController ]: 토큰 재발급 성공");
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "사용자 로그아웃 API", description = "사용자 로그아웃 API 입니다.")
    @DeleteMapping("/logout")
    public CustomResponse<?> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getUsername();
        authService.logout(email);
        return CustomResponse.onSuccess("로그아웃 성공");
    }

}
