package com.hanmin.loafy.global.exception.handler;

import com.hanmin.loafy.global.CustomResponse;
import com.hanmin.loafy.global.code.BaseErrorCode;
import com.hanmin.loafy.global.code.GeneralErrorCode;
import com.hanmin.loafy.global.exception.CustomException;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice // 모든 컨트롤러에서 발생한 예외를 한 곳에서 처리
public class GlobalExceptionHandler {
    // DTO 유효성 검사 실패시 해당 예외를 관련 정보를 함께 CustomResponse로 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<@NonNull CustomResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>(); // 에러 정보는 Map으로 저장
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
        );  // 예외에서 정보를 꺼내 각 필드 단위로 Map에 저장 (필드명, 메시지)

        BaseErrorCode validationErrorCode = GeneralErrorCode.VALIDATION_FAILED; // 에러 코드 생성
        CustomResponse<Map<String, String>> errorResponse = CustomResponse.onFailure(
                validationErrorCode.getCode(),
                validationErrorCode.getMessage(),
                errors
        ); // 에러 코드 정보와 Map에 저장한 예외의 정보를 CustomResponse로 생성
        // http 응답으로 만들어 반환
        return ResponseEntity.status(validationErrorCode.getHttpStatus()).body(errorResponse);
    }

    // Controller 메서드의 파라미터 검증 살패를 처리
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<@NonNull CustomResponse<Map<String, String>>> handleConstraintViolationException(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        // 검증 실패 목록을 하나씩 꺼내서 처리
        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.contains(".") ?
                    propertyPath.substring(propertyPath.lastIndexOf(".") + 1) : propertyPath;
            errors.put(fieldName, violation.getMessage());
        });

        BaseErrorCode constraintErrorCode = GeneralErrorCode.VALIDATION_FAILED;
        CustomResponse<Map<String, String>> errorResponse = CustomResponse.onFailure(
                constraintErrorCode.getCode(),
                constraintErrorCode.getMessage(),
                errors
        );
        return ResponseEntity.status(constraintErrorCode.getHttpStatus()).body(errorResponse);
    }

    // 애플리케이션에서 발생하는 커스텀 예외를 처리
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<@NonNull CustomResponse<Void>> handleCustomException(CustomException ex) {
        log.warn("[ CustomException ]: {}", ex.getBaseErrorCode().getMessage());
        return ResponseEntity.status(ex.getBaseErrorCode().getHttpStatus()).body(ex.getBaseErrorCode().getErrorResponse());
    }

    // 그 외 발생하는 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NonNull CustomResponse<String>> handleAllException(Exception ex) {
        log.error("[ Exception ]: Internal Server Error", ex);
        BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        CustomResponse<String> errorResponse = CustomResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage(),
                null
        );
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }
}
