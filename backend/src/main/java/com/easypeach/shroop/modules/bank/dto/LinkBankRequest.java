package com.easypeach.shroop.modules.bank.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinkBankRequest {

	@NotBlank
	@Size(min = 2, max = 255, message = "이름은 최소 2자 이상 입력해주세요")
	private String name;

	@NotBlank
	@Pattern(regexp = "^(\\d{1,})(-(\\d{1,})){1,}", message = "계좌번호 형식이 잘못되었습니다.")
	private String account;

	public static LinkBankRequest dtoForTest() {
		LinkBankRequest dto = new LinkBankRequest();
		dto.name = "아무개";
		dto.account = "010-12345-12345";
		return dto;
	}
}
