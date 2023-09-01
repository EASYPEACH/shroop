package com.easypeach.shroop.modules.bank.exception;

public class MinusMoneyException extends RuntimeException {

	private MinusMoneyException(String message) {
		super(message);
	}

	public static MinusMoneyException OverCharging() {
		return new MinusMoneyException("충전에 실패하셨습니다.");
	}
}
