package com.easypeach.shroop.modules.product.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreatedResponse {

	private Long productId;

	public ProductCreatedResponse(Long productId) {
		this.productId = productId;
	}

}
