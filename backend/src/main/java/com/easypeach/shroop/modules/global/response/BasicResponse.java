package com.easypeach.shroop.modules.global.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasicResponse {

	private String message;

	public BasicResponse(final String message) {
		this.message = message;
	}
	
}
