package com.easypeach.shroop.modules.product.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Member seller;

	@OneToOne(mappedBy = "product")
	private Transaction transaction;

	@Column(length = 100, nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "product_grade", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private ProductGrade productGrade;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImg> productImgList;

	@Column(length = 50, nullable = false)
	private String brand;

	@Column(nullable = false)
	private Long price;

	@Column(name = "is_checked_delivery_fee", nullable = false)
	private boolean isCheckedDeliveryFee;

	@Column(length = 255, nullable = false)
	private String content;

	@Column(name = "purchase_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate purchaseDate;

	@Column(name = "is_defect", nullable = false)
	private boolean isDefect;

	@Column(name = "sale_reason", length = 255, nullable = false)
	private String saleReason;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ProductStatus productStatus;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private LocalDateTime updateDate;

	public static Product createProduct(
		final Member seller,
		final ProductRequest productRequest,
		final Category category
	) {
		Product product = new Product();
		product.seller = seller;
		product.productStatus = ProductStatus.SELLING;
		return setByProductRequest(product, productRequest, category);
	}

	public static Product setByProductRequest(Product product, ProductRequest productRequest, Category category) {
		product.title = productRequest.getTitle();
		product.category = category;
		product.purchaseDate = productRequest.getPurchaseDate();
		product.productGrade = productRequest.getProductGrade();
		product.brand = productRequest.getBrand();
		product.price = productRequest.getPrice();
		product.isCheckedDeliveryFee = productRequest.isCheckedDeliveryFee();
		product.isDefect = productRequest.isDefect();
		product.saleReason = productRequest.getSaleReason();
		product.content = productRequest.getContent();
		return product;
	}

	public void updateProduct(
		final ProductRequest productRequest,
		final Category category
	) {
		setByProductRequest(this, productRequest, category);
	}

}
