package com.easypeach.shroop.modules.transaction.dto.response;

import java.time.LocalDateTime;

import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {
	private Long id;

	private LocalDateTime transactionCreateDate;

	private TransactionStatus transactionStatus;

	private String title;

	private Long price;

	private String productImgUrl;

	public HistoryResponse(Product product) {
		if (product.getTransaction() != null) {
			this.transactionCreateDate = product.getTransaction().getCreateDate();
			this.transactionStatus = product.getTransaction().getStatus();
		} else {
			this.transactionCreateDate = null;
			this.transactionStatus = null;
		}
		this.id = product.getId();
		this.title = product.getTitle();
		this.price = product.getPrice();
		this.productImgUrl = product.getProductImgList().get(0).getProductImgUrl();
	}
}
