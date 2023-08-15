package com.easypeach.shroop.modules.global.exception;

import com.easypeach.shroop.modules.auth.exception.InvalidTokenException;
import com.easypeach.shroop.modules.global.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError firstFieldError = e.getFieldErrors().get(0);
        String errorMessage = firstFieldError.getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
