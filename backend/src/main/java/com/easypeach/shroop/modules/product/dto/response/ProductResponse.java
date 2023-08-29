package com.easypeach.shroop.modules.product.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductGrade;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;

import lombok.Getter;

@Getter
public class ProductResponse {

	private Long id;
	private MemberResonse seller;
	private TransactionStatus transactionStatus;
	private String title;
	private Category category;
	private ProductGrade productGrade;
	private List<ProductImgResponse> productImgList;
	private String brand;
	private Long price;
	private Boolean isCheckedDeliveryFee;
	private String content;
	private LocalDateTime purchaseDate;
	private Boolean isDefect;
	private String saleReason;
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
	}

	public void setProductImgList(List<ProductImgResponse> productImgList) {
		this.productImgList = productImgList;
	}

	public void setSeller(MemberResonse seller) {
		this.seller = seller;
	}

	public void setTransaction(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

}
