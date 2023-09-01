package com.easypeach.shroop.modules.product.dto.response;

import java.time.LocalDateTime;

import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOneImgResponse {

	private Long id;

	private TransactionStatus transactionStatus;

	private String title;

	private String categoryName;

	private String productImgUrl;

	private Long likesCount;

	private Long price;

	private Boolean isCheckedDeliveryFee;

	private String content;

	private LocalDateTime createDate;

	private boolean isLike;

	public ProductOneImgResponse(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.categoryName = product.getCategory().getName();
		this.price = product.getPrice();
		this.isCheckedDeliveryFee = product.getIsCheckedDeliveryFee();
		this.content = product.getContent();
		this.createDate = product.getCreateDate();
		this.likesCount = product.getLikesCount();
		this.productImgUrl = product.getProductImgList().get(0).getProductImgUrl();
	}

	public ProductOneImgResponse(
		final Long id,
		final TransactionStatus transactionStatus,
		final String title,
		final String categoryName,
		final String productImgUrl,
		final Long likesCount,
		final Long price,
		final Boolean isCheckedDeliveryFee,
		final String content,
		final LocalDateTime createDate,
		final boolean isLike
	) {
		this.id = id;
		this.transactionStatus = transactionStatus;
		this.title = title;
		this.categoryName = categoryName;
		this.productImgUrl = productImgUrl;
		this.likesCount = likesCount;
		this.price = price;
		this.isCheckedDeliveryFee = isCheckedDeliveryFee;
		this.content = content;
		this.createDate = createDate;
		this.isLike = isLike;
	}

}
