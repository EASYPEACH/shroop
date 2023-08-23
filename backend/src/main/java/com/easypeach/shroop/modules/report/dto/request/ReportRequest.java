package com.easypeach.shroop.modules.report.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class ReportRequest {

	@NotBlank
	@Size(min = 5, max = 255, message = "최소 5자 이상 입력해주세요")
	private String title;

	@NotNull
	@JsonProperty("isMediate")
	private boolean isMediate;

	//@NotNull
	// private Long productId;

	@NotBlank
	@Size(min = 5, max = 255, message = "최소 5자 이상 입력해주세요")
	private String content;

	public ReportRequest(
		final String title,
		final boolean isMediate,
		// final Long productId,
		final String content
	) {
		this.title = title;
		this.isMediate = isMediate;
		// this.productId = productId;
		this.content = content;
	}

}
