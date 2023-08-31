package com.easypeach.shroop.modules.member.dto.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileEditForm {
	private String nickname;
	private String phoneNumber;
	private String profileImg;

	public ProfileEditForm(String nickname, String phoneNumber, String profileImg) {
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.profileImg = profileImg;
	}
}
