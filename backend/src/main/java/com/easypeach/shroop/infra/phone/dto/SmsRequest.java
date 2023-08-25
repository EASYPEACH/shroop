package com.easypeach.shroop.infra.phone.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SmsRequest {

	private String type;

	private String contentType;

	private String countryCode;

	private String from;

	private String content;

	private List<MessageRequest> messages;

	public SmsRequest(String type, String contentType, String countryCode, String from, String content,
		List<MessageRequest> messages) {
		this.type = type;
		this.contentType = contentType;
		this.countryCode = countryCode;
		this.from = from;
		this.content = content;
		this.messages = messages;
	}
}
