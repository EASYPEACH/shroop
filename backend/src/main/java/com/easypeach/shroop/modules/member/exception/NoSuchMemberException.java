package com.easypeach.shroop.modules.member.exception;

public class NoSuchMemberException extends RuntimeException {
	public NoSuchMemberException() {
		super();
	}

	public NoSuchMemberException(String msg) {
		super(msg);
	}
}
