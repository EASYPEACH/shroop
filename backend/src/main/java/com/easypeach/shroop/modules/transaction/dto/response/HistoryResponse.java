package com.easypeach.shroop.modules.transaction.dto.response;

import java.time.LocalDateTime;

import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {

	private LocalDateTime transactionCreateDate;

	private TransactionStatus transactionStatus;

	private String title;

	private Long price;

	private String productImgUrl;

	public HistoryResponse(Transaction transaction) {
		this.transactionCreateDate = transaction.getCreateDate();
		this.transactionStatus = transaction.getStatus();
		this.title = transaction.getProduct().getTitle();
		this.price = transaction.getProduct().getPrice();
		this.productImgUrl = transaction.getProduct().getProductImgList().get(0).getProductImgUrl();
	}

	public HistoryResponse(Product product) {
		if (product.getTransaction() != null) {
			this.transactionCreateDate = product.getTransaction().getCreateDate();
			this.transactionStatus = product.getTransaction().getStatus();
		} else {
			this.transactionCreateDate = null;
			this.transactionStatus = null;
		}
		this.title = product.getTitle();
		this.price = product.getPrice();
		this.productImgUrl = product.getProductImgList().get(0).getProductImgUrl();
	}
}
