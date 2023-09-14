package com.easypeach.shroop.modules.product.dto.response;

import com.easypeach.shroop.modules.product.domain.ProductReturnImg;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductReturnImgResponse {

	private Long id;

	private String imgUrl;

	public ProductReturnImgResponse(Long id, String imgUrl) {
		this.id = id;
		this.imgUrl = imgUrl;
	}

	public ProductReturnImgResponse(ProductReturnImg productReturnImg) {
		this.id = productReturnImg.getId();
		this.imgUrl = productReturnImg.getImgUrl();
	}
}
