package com.easypeach.shroop.modules.product.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductReturnResponse {

	private Long id;

	private String productTitle;

	private String content;

	private List<ProductReturnImgResponse> productReturnImgResponseList;

	public ProductReturnResponse(Long id, String productTitle, String content) {
		this.id = id;
		this.productTitle = productTitle;
		this.content = content;
	}

	public void updateProductReturnImgResponseList(List<ProductReturnImgResponse> productReturnImgResponseList) {
		this.productReturnImgResponseList = productReturnImgResponseList;
	}
}
