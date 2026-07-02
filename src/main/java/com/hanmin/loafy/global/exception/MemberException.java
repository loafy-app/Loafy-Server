package com.hanmin.loafy.global.exception;

import com.hanmin.loafy.global.code.BaseErrorCode;

public class MemberException extends CustomException {

    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
