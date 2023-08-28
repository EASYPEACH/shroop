package com.easypeach.shroop.modules.transaction.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BuyerResponse {

	private String name;

	private String location;

	private String phone_number;

	public BuyerResponse(final String name, final String location, final String phone_number) {
		this.name = name;
		this.location = location;
		this.phone_number = phone_number;
	}
}
