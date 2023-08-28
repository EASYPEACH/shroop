package com.easypeach.shroop.modules.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginCheckResponse {
	private Long memberId;
	private String loginId;
	private String nickname;
	private boolean result;

	public LoginCheckResponse(Long memberId, String loginId, String nickname, boolean result) {
		this.memberId = memberId;
		this.loginId = loginId;
		this.nickname = nickname;
		this.result = result;
	}
}
