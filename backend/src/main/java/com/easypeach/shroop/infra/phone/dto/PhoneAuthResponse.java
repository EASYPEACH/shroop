package com.easypeach.shroop.infra.phone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PhoneAuthResponse {

	private String accessKey;

	private String serviceId;

	private String sender;

	private String signature;

	private String time;

	public PhoneAuthResponse(final String accessKey, final String serviceId,
		final String sender, final String signature,
		final String time) {
		this.accessKey = accessKey;
		this.serviceId = serviceId;
		this.sender = sender;
		this.signature = signature;
		this.time = time;
	}
}
