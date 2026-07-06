package com.hanmin.loafy.domain.auth.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
