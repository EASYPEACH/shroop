package com.easypeach.shroop.infra.phone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MessageRequest {

	private String to;

	private String content;

	public MessageRequest(final String to, final String content) {
		this.to = to;
		this.content = content;
	}

}
