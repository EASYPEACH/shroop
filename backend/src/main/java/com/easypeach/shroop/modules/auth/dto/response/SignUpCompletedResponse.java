package com.easypeach.shroop.modules.auth.dto.response;

import lombok.Getter;

@Getter
public class SignUpCompletedResponse {
	private Long memberId;
	private String nickname;

	public SignUpCompletedResponse() {
	}

	;

	public SignUpCompletedResponse(Long memberId, String nickname) {
		this.memberId = memberId;
		this.nickname = nickname;
	}
}
