package com.easypeach.shroop.modules.transaction.exception;

public class SellerPurchaseException extends RuntimeException {

	private SellerPurchaseException(String message) {
		super(message);
	}

	public static SellerPurchaseException SellerBuyingMyProduct() {
		return new SellerPurchaseException("본인의 상품을 구매할 수 없습니다.");
	}
}
