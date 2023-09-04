package com.easypeach.shroop.modules.transaction.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BuyerResponse {

	private String name;

	private String location;

	private String phoneNumber;

	public BuyerResponse(final String name, final String location, final String phoneNumber) {
		this.name = name;
		this.location = location;
		this.phoneNumber = phoneNumber;
	}
}
