package com.hanmin.loafy.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthErrorCode implements BaseErrorCode{

    AUTH_UNAUTHORIZED_401(HttpStatus.UNAUTHORIZED, "AUTH401_0", "이메일 또는 비밀번호가 올바르지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
