package com.easypeach.shroop.modules.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DuplicateValidResponse {

	private boolean result;
	private String message;

	public DuplicateValidResponse(final boolean result, final String message) {
		this.result = result;
		this.message = message;
	}
}
