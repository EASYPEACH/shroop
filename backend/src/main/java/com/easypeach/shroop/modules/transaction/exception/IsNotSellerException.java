package com.easypeach.shroop.modules.transaction.exception;

public class IsNotSellerException extends RuntimeException {

	private IsNotSellerException(String message) {
		super(message);
	}

	public static IsNotSellerException isNotSellerException() {
		return new IsNotSellerException("해당 거래의 판매자가 아닙니다.");
	}
}
