package com.easypeach.shroop.modules.member.domain;

public enum Role {
	ROLE_DELETE("로그인에 실패하였습니다"),
	ROLE_NOT_AUTH_USER("차단된 계정입니다"),
	ROLE_USER("로그인에 성공하셨습니다"),
	ROLE_ADMIN("관리자 계정으로 로그인하였습니다");

	private String responseMessage;

	Role(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return this.responseMessage;
	}
}
