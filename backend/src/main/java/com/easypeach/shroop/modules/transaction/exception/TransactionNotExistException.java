package com.easypeach.shroop.modules.transaction.exception;

public class TransactionNotExistException extends RuntimeException {

	private TransactionNotExistException(String message) {
		super(message);
	}

	public static TransactionNotExistException transactionNotExistException() {
		return new TransactionNotExistException("거래정보가 존재하지 않습니다.");
	}
}
