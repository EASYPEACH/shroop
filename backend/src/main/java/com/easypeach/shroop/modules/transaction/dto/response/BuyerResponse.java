package com.easypeach.shroop.modules.transaction.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BuyerResponse {

	private String name;

	private String phoneNumber;

	private String postcode;

	private String location;

	private String detailLocation;

	public BuyerResponse(final String name, final String phoneNumber, final String postcode, final String location,
		final String detailLocation) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.postcode = postcode;
		this.location = location;
		this.detailLocation = detailLocation;
	}
}
