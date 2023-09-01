package com.easypeach.shroop.modules.product.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SearchProductResponse {

	private int pageCount;

	private List<ProductOneImgResponse> productList;

	public SearchProductResponse(int pageCount, List<ProductOneImgResponse> productList) {
		this.pageCount = pageCount;
		this.productList = productList;
	}
}
