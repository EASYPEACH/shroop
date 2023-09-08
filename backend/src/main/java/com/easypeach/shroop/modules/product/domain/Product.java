package com.easypeach.shroop.modules.product.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicInsert
@DynamicUpdate
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
	private Member seller;

	@OneToOne(mappedBy = "product")
	private Transaction transaction;

	@Column(length = 100, nullable = false)
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "product_grade", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private ProductGrade productGrade;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImg> productImgList = new ArrayList<>();

	@Column(length = 50, nullable = false)
	private String brand;

	@Column(nullable = false)
	private Long price;

	@Column(name = "is_checked_delivery_fee", nullable = false)
	private Boolean isCheckedDeliveryFee;

	@Column(length = 255, nullable = false)
	private String content;

	@Column(name = "purchase_date", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate purchaseDate;

	@Column(name = "is_defect", nullable = false)
	private Boolean isDefect;

	@Column(name = "sale_reason", length = 255, nullable = false)
	private String saleReason;

	@Column(name = "likes")
	@ColumnDefault(value = "0")
	private Long likesCount;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private LocalDateTime updateDate;

	public static Product createProduct(
		final Member seller,
		final ProductRequest productRequest,
		final Category category,
		final List<ProductImg> productImgs
	) {
		Product product = new Product();
		product.seller = seller; //판매자 설정
		product.category = category; // 카테고리 설정
		product.setByProductRequest(productRequest); // 상품 내용 설정
		for (ProductImg productImg : productImgs) { //이미지 설정
			product.addProductImg(productImg);
		}

		return product;
	}

	public void setByProductRequest(ProductRequest productRequest) {
		this.title = productRequest.getTitle();
		this.purchaseDate = productRequest.getPurchaseDate();
		this.productGrade = productRequest.getProductGrade();
		this.brand = productRequest.getBrand();
		this.price = productRequest.getPrice();
		this.isCheckedDeliveryFee = productRequest.getIsCheckedDeliveryFee();
		this.isDefect = productRequest.getIsDefect();
		this.saleReason = productRequest.getSaleReason();
		this.content = productRequest.getContent();
	}

	public void updateProduct(
		final ProductRequest productRequest,
		final Category category
	) {
		setByProductRequest(productRequest);
		this.category = category;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}

	public void addProductImg(ProductImg productImg) {
		this.productImgList.add(productImg);
		productImg.setProduct(this);
	}
}
