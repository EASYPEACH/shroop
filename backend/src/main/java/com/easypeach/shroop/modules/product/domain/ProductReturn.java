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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_return")
@Entity
public class ProductReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(length = 255, nullable = false)
	private String content;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	public static ProductReturn createProductReturn(
		final Product product,
		final String content
	) {
		ProductReturn productReturn = new ProductReturn();
		productReturn.product = product;
		productReturn.content = content;
		return productReturn;
	}

}
