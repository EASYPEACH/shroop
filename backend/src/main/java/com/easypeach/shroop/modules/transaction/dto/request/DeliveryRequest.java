package com.easypeach.shroop.modules.transaction.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeliveryRequest {

	private String trackingNumber;

	private String parcel;

	public DeliveryRequest(String trackingNumber, String parcel) {
		this.trackingNumber = trackingNumber;
		this.parcel = parcel;
	}
}
