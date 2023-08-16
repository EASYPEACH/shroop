package com.easypeach.shroop.modules.product.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "product_img")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ProductImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "img_url", length = 255, nullable = false)
	private String imgUrl;

	@Column(name = "defect_img", nullable = false)
	private boolean defectImg;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDate createDate;

}
