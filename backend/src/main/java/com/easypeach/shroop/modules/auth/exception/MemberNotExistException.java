package com.easypeach.shroop.modules.auth.exception;

public class MemberNotExistException extends RuntimeException {
	public MemberNotExistException() {
		super();
	}

	public MemberNotExistException(String msg) {
		super(msg);
	}
}
