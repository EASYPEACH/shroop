package com.easypeach.shroop.modules.auth.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 폰 인증을 요청할때 전달하는 DTO
@Getter
@NoArgsConstructor
public class PhoneAuthRequest {

	@NotNull
	private long uuid;

	@NotBlank
	@Pattern(regexp = "^0\\d{1,2}\\d{3,4}\\d{4}$")
	private String phoneNumber;

	@NotBlank
	private String phoneAuthNumber;

	public PhoneAuthRequest(Long uuid, String phoneNumber, String phoneAuthNumber) {
		this.uuid = uuid;
		this.phoneNumber = phoneNumber;
		this.phoneAuthNumber = phoneAuthNumber;
	}
}
