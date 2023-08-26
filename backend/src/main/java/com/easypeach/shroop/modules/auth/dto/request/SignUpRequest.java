package com.easypeach.shroop.modules.auth.dto.request;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class SignUpRequest {

	@NotBlank
	@Size(min = 5, max = 255, message = "최소 5자 이상 입력해주세요")
	@Pattern(regexp = "^[a-zA-Z0-9]*$")
	private String loginId;

	@NotBlank
	@Size(min = 5, max = 255, message = "최소 5자 이상 입력해주세요")
	@Pattern(regexp = "^.*(?=^.{8,30}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")
	private String password;

	@NotBlank
	@Size(min = 2, max = 255, message = "최소 2자 이상 입력해주세요")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]*$")
	private String nickname;

	@NotBlank
	@Pattern(regexp = "^010\\d{7,8}$")
	private String phoneNumber;

	@NotNull
	@AssertTrue(message = "약관에 동의가 필요합니다")
	private Boolean agreeShroop;

	@NotNull
	@AssertTrue(message = "약관에 동의가 필요합니다")
	private Boolean agreePersonal;

	@NotNull
	@AssertTrue(message = "약관에 동의가 필요합니다")
	private Boolean agreeIdentify;

	public SignUpRequest(String loginId, String password, String nickname, String phoneNumber
		, Boolean agreeShroop, Boolean agreePersonal, Boolean agreeIdentify) {
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.agreeShroop = agreeShroop;
		this.agreePersonal = agreePersonal;
		this.agreeIdentify = agreeIdentify;
	}
}
