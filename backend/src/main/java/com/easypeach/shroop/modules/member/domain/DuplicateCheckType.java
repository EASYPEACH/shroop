package com.easypeach.shroop.modules.member.domain;

public enum DuplicateCheckType {
	NICK_NAME("nickname"), LOGIN_ID("loginId"), PHONE_NUMBER("phoneNumber");

	private String name;

	DuplicateCheckType(String name) {
		this.name = name;
	}

	public static DuplicateCheckType createCheckType(String name) {
		if (name.equals("nickname")) {
			return DuplicateCheckType.NICK_NAME;
		}
		if (name.equals("loginId")) {
			return DuplicateCheckType.LOGIN_ID;
		}
		if (name.equals("phoneNumber")) {
			return DuplicateCheckType.PHONE_NUMBER;
		}

		return null;
	}

	public String getName() {
		return this.name;
	}
}
