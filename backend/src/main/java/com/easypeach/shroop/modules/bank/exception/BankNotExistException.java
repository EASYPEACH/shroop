package com.easypeach.shroop.modules.bank.exception;

public class BankNotExistException extends RuntimeException {

	private BankNotExistException(String message) {
		super(message);
	}

	public static BankNotExistException bankNotExistException() {
		return new BankNotExistException("계좌정보가 존재하지 않습니다.");
	}
}
