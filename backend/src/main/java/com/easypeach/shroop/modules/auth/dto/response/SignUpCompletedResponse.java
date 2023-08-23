package com.easypeach.shroop.modules.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpCompletedResponse {
	private Long memberId;
	private String nickname;

	public SignUpCompletedResponse(final Long memberId, final String nickname) {
		this.memberId = memberId;
		this.nickname = nickname;
	}
}
