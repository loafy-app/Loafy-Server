package com.hanmin.loafy.global.jwt;

public record JwtDTO(
        String JwtAccessToken,
        String JwtRefreshToken
) {
}
