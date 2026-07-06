package com.hanmin.loafy.global.exception;

import com.hanmin.loafy.global.code.BaseErrorCode;

public class AuthException extends CustomException {

    public AuthException(BaseErrorCode errorCode) { super(errorCode); }

}
