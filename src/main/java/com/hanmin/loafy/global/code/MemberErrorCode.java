package com.hanmin.loafy.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum MemberErrorCode implements BaseErrorCode {

    SAME_NICKNAME(HttpStatus.BAD_REQUEST, "MEMBER400_0", "기존과 동일한 닉네임입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_0", "사용자를 찾을 수 없습니다."),
    DUPLICATE_MEMBER(HttpStatus.CONFLICT, "MEMBER409_0", "중복된 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
