package com.easypeach.shroop.modules.member.exception;

public class PasswordNotMatchException extends RuntimeException {
	public PasswordNotMatchException(String message) {
		super(message);
	}
}
