package com.easypeach.shroop.modules.auth.exception;

public class UnAuthorizedAccessException extends RuntimeException {
	public UnAuthorizedAccessException(String message) {
		super(message);
	}
}
