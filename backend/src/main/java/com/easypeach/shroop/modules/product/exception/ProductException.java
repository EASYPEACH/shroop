package com.easypeach.shroop.modules.product.exception;

import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;

public class ProductException extends RuntimeException {
	private ProductException(String message) {
		super(message);
	}

	public static ProductException noSuchProductException() {
		return new ProductException("존재하지 않는 상품입니다.");
	}

	public static ProductException notStatusDelete(TransactionStatus status) {
		return new ProductException(status + "상태에서는 삭제할 수 없습니다.");
	}

}
