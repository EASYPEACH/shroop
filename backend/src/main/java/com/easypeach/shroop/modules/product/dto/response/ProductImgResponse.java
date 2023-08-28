package com.easypeach.shroop.modules.product.dto.response;

import com.easypeach.shroop.modules.product.domain.ProductImg;

import lombok.Getter;

@Getter
public class ProductImgResponse {
	private Long id;
	private String productImgUrl;
	private Boolean isDefect;

	public ProductImgResponse(ProductImg productImg) {
		this.id = productImg.getId();
		this.productImgUrl = productImg.getProductImgUrl();
		this.isDefect = productImg.getIsDefect();
	}
}
