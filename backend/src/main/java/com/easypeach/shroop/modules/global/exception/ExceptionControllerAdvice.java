package com.easypeach.shroop.modules.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easypeach.shroop.modules.auth.exception.PhoneAuthFailException;
import com.easypeach.shroop.modules.auth.exception.PhoneAuthNotExistException;
import com.easypeach.shroop.modules.bank.exception.BankNotExistException;
import com.easypeach.shroop.modules.bank.exception.MinusMoneyException;
import com.easypeach.shroop.modules.global.exception.dto.ErrorResponse;
import com.easypeach.shroop.modules.member.exception.DuplicateValueException;
import com.easypeach.shroop.modules.member.exception.MemberNotExistException;
import com.easypeach.shroop.modules.member.exception.MinusPointException;
import com.easypeach.shroop.modules.member.exception.PasswordNotMatchException;
import com.easypeach.shroop.modules.product.exception.CategoryNotFoundException;
import com.easypeach.shroop.modules.product.exception.ProductImgLengthException;
import com.easypeach.shroop.modules.transaction.exception.LackOfPointException;
import com.easypeach.shroop.modules.transaction.exception.SellerPurchaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	@ExceptionHandler({
		MemberNotExistException.class,
		PhoneAuthNotExistException.class,
		CategoryNotFoundException.class,
		BankNotExistException.class
	})
	public ResponseEntity<ErrorResponse> handleNotExistException(final RuntimeException e) {
		String errorMessage = e.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return ResponseEntity.status(404).body(errorResponse);
	}

	@ExceptionHandler({
		SellerPurchaseException.class,
		MinusPointException.class,
		MinusMoneyException.class,
		ProductImgLengthException.class,
		DuplicateValueException.class,
		PasswordNotMatchException.class,
		PhoneAuthFailException.class,
		LackOfPointException.class
	})
	public ResponseEntity<ErrorResponse> handleAuthException(final RuntimeException e) {
		String errorMessage = e.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(final Exception e) {
		String errorMessage = e.getMessage();
		log.error(e.getMessage());
		ErrorResponse errorResponse = new ErrorResponse("내부 서버에 문제가 발생하여 확인 중 입니다");
		return ResponseEntity.internalServerError().body(errorResponse);
	}

}
