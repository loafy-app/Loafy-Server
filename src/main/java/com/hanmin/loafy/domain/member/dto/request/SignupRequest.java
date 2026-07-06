package com.hanmin.loafy.domain.member.dto.request;

public record SignupRequest(
        String email,
        String password
) {
}
