package com.easypeach.shroop.modules.product.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductReturnRequest {

	@NotBlank
	@Size(min = 30, max = 255, message = "최소 30자 이상 입력해주세요")
	private String content;

	public ProductReturnRequest(String content) {
		this.content = content;
	}
}
