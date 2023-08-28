package com.easypeach.shroop.modules.product.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequest {

	@NotBlank
	@Size(min = 2, max = 255, message = "최소 2자 이상 입력해주세요")
	private String name;

}
