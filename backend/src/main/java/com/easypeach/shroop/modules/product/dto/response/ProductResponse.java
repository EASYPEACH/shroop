package com.easypeach.shroop.modules.product.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductGrade;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

	private Long id;
	private MemberResponse seller;
	private TransactionStatus transactionStatus;
	private String title;
	private Category category;
	private ProductGrade productGrade;
	private List<ProductImgResponse> productImgList;
	private Long likesCount;
	private Boolean isLike = false;
	private String brand;
	private Long price;
	private Boolean isCheckedDeliveryFee;
	private String content;
	private Boolean isDefect;
	private String saleReason;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate purchaseDate;

	private LocalDateTime createDate;

	public ProductResponse(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.category = product.getCategory();
		this.productGrade = product.getProductGrade();
		this.brand = product.getBrand();
		this.price = product.getPrice();
		this.isCheckedDeliveryFee = product.getIsCheckedDeliveryFee();
		this.content = product.getContent();
		this.purchaseDate = product.getPurchaseDate();
		this.isDefect = product.getIsDefect();
		this.saleReason = product.getSaleReason();
		this.createDate = product.getCreateDate();
		this.likesCount = product.getLikesCount();
		this.productImgList = product.getProductImgList()
			.stream()
			.map(ProductImgResponse::new)
			.collect(
				Collectors.toList());
		this.seller = new MemberResponse(product.getSeller());
	}

	public void setTransaction(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public void setIsLike() {
		this.isLike = true;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class ProductImgResponse {
		private Long id;
		private String productImgUrl;
		private Boolean isDefect;

		public ProductImgResponse(ProductImg productImg) {
			this.id = productImg.getId();
			this.productImgUrl = productImg.getProductImgUrl();
			this.isDefect = productImg.getIsDefect();
		}
	}

	@Getter
	@AllArgsConstructor
	public static class MemberResponse {
		private Long id;

		private String nickName;

		public MemberResponse(Member member) {
			this.id = member.getId();
			this.nickName = member.getNickname();
		}
	}

}
