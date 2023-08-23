package com.easypeach.shroop.modules.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginSuccessResponse {
	private String loginId;
	private String nickname;

	public LoginSuccessResponse(final String loginId, final String nickname) {
		this.loginId = loginId;
		this.nickname = nickname;
	}
}
