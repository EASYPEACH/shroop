package com.easypeach.shroop.modules.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easypeach.shroop.modules.global.exception.dto.ErrorResponse;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		final MethodArgumentNotValidException e) {
		FieldError firstFieldError = e.getFieldErrors().get(0);
		String errorMessage = firstFieldError.getDefaultMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);

		return ResponseEntity.badRequest().body(errorResponse);
	}
}
