package com.hanmin.loafy.global.security.jwt;

public record JwtDTO(
        String jwtAccessToken,
        String jwtRefreshToken
) {
}
