package com.easypeach.shroop.modules.transaction.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeliveryRequest {

	@NotBlank
	@Pattern(regexp = "^[0-9]*$")
	private String trackingNumber;

	@NotBlank
	private String parcel;

	public DeliveryRequest(String trackingNumber, String parcel) {
		this.trackingNumber = trackingNumber;
		this.parcel = parcel;
	}
}
