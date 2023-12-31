package com.bupt.software.exception;

import com.bupt.software.dto.response.APIResponse;
import com.bupt.software.utils.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> handleCustomException(Exception ex) {
        APIResponse<String> apiResponse = APIResponse.<String>builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(Constant.RESULT_KO)
                .message(ex.getMessage())
                .internalCode(Constant.GENERIC_RESPONSE_CODE)
                .data(null)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
