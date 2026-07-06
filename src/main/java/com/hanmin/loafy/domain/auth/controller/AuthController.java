package com.hanmin.loafy.domain.auth.controller;

import com.hanmin.loafy.domain.auth.dto.request.LoginRequest;
import com.hanmin.loafy.domain.auth.service.AuthService;
import com.hanmin.loafy.global.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API", description = "사용자 인증/인가 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation
    @PostMapping("/login")
    public CustomResponse<?> emailLogin(@RequestBody LoginRequest request) {
        return CustomResponse.onSuccess(authService.login(request));
    }

}
