package com.easypeach.shroop.modules.product.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class ProductDeleteRequest {
	private Long productId;

	public ProductDeleteRequest(Long productId) {
		this.productId = productId;
	}
}
