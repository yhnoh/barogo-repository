package com.example.barogo.exception;

import com.example.barogo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * exception 처리하기
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> businessExceptionHandler(BusinessException e){
        ApiResponse<Object> error = ApiResponse.error(e);
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }
}
