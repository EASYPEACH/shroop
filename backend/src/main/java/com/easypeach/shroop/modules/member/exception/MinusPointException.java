package com.easypeach.shroop.modules.member.exception;

public class MinusPointException extends RuntimeException {

	private MinusPointException(String message) {
		super(message);
	}

	public static MinusPointException OverExchanging() {
		return new MinusPointException("보유한 포인트보다 환전하려는 포인트가 더 큽니다. 다시 입력해주세요");
	}
}
