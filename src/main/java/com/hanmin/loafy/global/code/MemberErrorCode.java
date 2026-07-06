package com.hanmin.loafy.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_CONFLICT_409(HttpStatus.CONFLICT, "MEMBER409_0", "중복된 회원입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
