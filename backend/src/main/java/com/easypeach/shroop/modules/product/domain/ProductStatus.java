package com.easypeach.shroop.modules.product.domain;

import lombok.Getter;

@Getter
public enum ProductStatus {

	SELLING("판매중"),
	PURCHASE_REQUEST("거래요청"),
	SHIPPING("베송중"),
	DELIVERY_COMPLETE("배송완료"),
	TRANSACTION_COMPLETE("거래완료"),
	RETURN_REQUEST("반품요청"),
	RETURN_COMPLETE("반품완료");
	private final String status;

	ProductStatus(String status) {
		this.status = status;
	}

}
