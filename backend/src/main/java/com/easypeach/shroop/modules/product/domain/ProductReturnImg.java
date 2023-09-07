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

@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_return_img")
@Entity
public class ProductReturnImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_report_id", nullable = false)
	private ProductReturn productReturn;

	@Column(name = "img_url", length = 255, nullable = false)
	private String imgUrl;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	public static ProductReturnImg createProductReturnImg(
		final ProductReturn productReturn,
		final String imgUrl
	) {
		ProductReturnImg productReturnImg = new ProductReturnImg();
		productReturnImg.productReturn = productReturn;
		productReturnImg.imgUrl = imgUrl;
		return productReturnImg;
	}

}
