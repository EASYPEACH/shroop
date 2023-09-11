package com.easypeach.shroop.modules.transaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreatedResponse {
	private Long transactionId;
	private Long productId;
	private String productImgUrl;
	private String productTitle;
	private Long productPrice;
	private String buyerName;
	private String buyerPhoneNumber;
	private String buyerPostcode;
	private String buyerLocation;
	private String buyerDetailLocation;

}
