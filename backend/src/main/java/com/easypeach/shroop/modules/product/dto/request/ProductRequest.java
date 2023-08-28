package com.easypeach.shroop.modules.product.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.easypeach.shroop.modules.product.domain.ProductGrade;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequest {

	@NotBlank
	@Size(min = 5, max = 255, message = "최소 5자 이상 입력주세요")
	private String title;

	@NotNull
	private Long categoryId;

	@NotBlank
	@Size(min = 2, max = 255, message = "최소 2자 이상 입력해주세요")
	private String brand;

	@NotNull
	private Long price;

	@NotNull
	private Boolean isCheckedDeliveryFee;

	@NotNull
	private LocalDate purchaseDate;

	@NotNull
	private ProductGrade productGrade;

	@NotNull
	private Boolean isDefect;

	@NotBlank
	@Size(min = 5, max = 255, message = "최소 5자 이상 입력주세요")
	private String saleReason;

	@NotBlank
	@Size(min = 30, max = 255, message = "최소 30자 이상 입력주세요")
	private String content;

	public ProductRequest(final String title, final Long categoryId,
		final String brand, final Long price, final Boolean isCheckedDeliveryFee, final LocalDate purchaseDate,
		final ProductGrade productGrade, final Boolean isDefect, final String saleReason, final String content) {
		this.title = title;
		this.categoryId = categoryId;
		this.brand = brand;
		this.price = price;
		this.isCheckedDeliveryFee = isCheckedDeliveryFee;
		this.purchaseDate = purchaseDate;
		this.productGrade = productGrade;
		this.isDefect = isDefect;
		this.saleReason = saleReason;
		this.content = content;
	}
}
