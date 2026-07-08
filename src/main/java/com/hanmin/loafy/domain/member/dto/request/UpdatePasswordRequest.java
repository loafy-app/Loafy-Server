package com.hanmin.loafy.domain.member.dto.request;

public record UpdatePasswordRequest(
        String newPassword,
        String newPasswordConfirm
) {
}
