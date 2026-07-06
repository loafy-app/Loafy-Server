package com.hanmin.loafy.global.security.handler;

import com.hanmin.loafy.global.CustomResponse;
import com.hanmin.loafy.global.code.GeneralErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                         @NonNull AuthenticationException authenticationException) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(401);
        CustomResponse<Object> errorResponse = CustomResponse.onFailure(
                GeneralErrorCode.UNAUTHORIZED_401.getCode(),
                GeneralErrorCode.UNAUTHORIZED_401.getMessage(),
                null
        );
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }

}
