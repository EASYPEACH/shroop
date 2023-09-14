package com.easypeach.shroop.modules.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ProfileEditRequest {

	@Size(min = 2, max = 255, message = "닉네임은 최소 2자 이상 입력해주세요")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]*$")
	private String nickname;
	private String oldPassword;
	private String newPassword;
	private long uuid;
	@NotBlank
	@Pattern(regexp = "^0\\d{1,2}\\d{3,4}\\d{4}$", message = "휴대전화번호 형식이 잘못되었습니다")
	private String phoneNumber;
	private String phoneAuthNumber;

	public ProfileEditRequest(String nickname, String oldPassword, String newPassword, long uuid, String phoneNumber,
		String phoneAuthNumber) {
		this.nickname = nickname;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.uuid = uuid;
		this.phoneNumber = phoneNumber;
		this.phoneAuthNumber = phoneAuthNumber;
	}
}
