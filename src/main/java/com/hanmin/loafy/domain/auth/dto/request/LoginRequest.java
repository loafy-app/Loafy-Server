package com.hanmin.loafy.domain.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
