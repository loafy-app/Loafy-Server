package com.hanmin.loafy.global.exception;

import com.hanmin.loafy.global.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    public CustomException(BaseErrorCode baseErrorCode) {
        this.baseErrorCode = baseErrorCode;
    }

    private final BaseErrorCode baseErrorCode;

}
