package com.easypeach.shroop.modules.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SearchRequest {

	private String title;

	private Long categoryId;

	@JsonProperty("hasNotTransaction")
	private boolean hasNotTransaction;

	public SearchRequest(String title, Long categoryId, boolean hasNotTransaction) {
		this.title = title;
		this.categoryId = categoryId;
		this.hasNotTransaction = hasNotTransaction;
	}
}
