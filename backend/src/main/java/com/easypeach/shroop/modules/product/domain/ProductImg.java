package com.easypeach.shroop.modules.product.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "product_img")
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class ProductImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "product_img_url", length = 255, nullable = false)
	private String productImgUrl;

	@Column(name = "is_defect", nullable = false)
	private Boolean isDefect;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	public static ProductImg createProductImg(final String productImgUrl, final boolean isDefect) {
		ProductImg productImg = new ProductImg();
		productImg.productImgUrl = productImgUrl;
		productImg.isDefect = isDefect;
		return productImg;
	}

	public void setProduct(Product product) {
		this.product = product;
		product.getProductImgList().add(this);
	}

}
