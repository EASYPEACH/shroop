package com.easypeach.shroop.modules.product.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponse {
	private Long id;
	private String name;

	public CategoryResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
