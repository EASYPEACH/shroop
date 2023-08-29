package com.easypeach.shroop.modules.transaction.exception;

public class IsNotBuyerException extends RuntimeException {

	private IsNotBuyerException(String message) {
		super(message);
	}

	public static IsNotBuyerException isNotBuyerException() {
		return new IsNotBuyerException("해당 거래의 구매자가 아닙니다.");
	}
}
