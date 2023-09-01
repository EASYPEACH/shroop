package com.easypeach.shroop.modules.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SearchRequest {

	private String title;

	private Long categoryId;

	private Boolean hasNotTransaction;

	public SearchRequest(String title, Long categoryId, Boolean hasNotTransaction) {
		this.title = title;
		this.categoryId = categoryId;
		this.hasNotTransaction = hasNotTransaction;
	}
	
}
