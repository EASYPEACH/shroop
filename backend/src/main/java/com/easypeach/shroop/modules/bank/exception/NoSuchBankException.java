package com.easypeach.shroop.modules.bank.exception;

public class NoSuchBankException extends RuntimeException {

	private NoSuchBankException(String message) {
		super(message);
	}

	public static NoSuchBankException bankNotExistException() {
		return new NoSuchBankException("계좌정보가 존재하지 않습니다.");
	}
}
