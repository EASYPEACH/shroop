package com.easypeach.shroop.modules.transaction.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateRequest {

	@NotBlank
	@Size(min = 2, max = 255, message = "최소 2자 이상 입력해주세요")
	private String buyerName;

	@NotBlank
	@Pattern(regexp = "^0\\d{1,2}\\d{3,4}\\d{4}$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
	private String buyerPhoneNumber;

	@NotBlank
	@Size(min = 5, max = 255, message = "최소 5자 이상 입력해주세요")
	private String buyerLocation;
}
