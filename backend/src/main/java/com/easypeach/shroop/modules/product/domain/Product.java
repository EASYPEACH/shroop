package com.easypeach.shroop.modules.product.domain;

import java.time.LocalDate;

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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.easypeach.shroop.modules.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Member seller;

	@ManyToOne
	@JoinColumn(name = "buyer_id", nullable = false)
	private Member buyer;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private ProductGrade grade;

	@Column(length = 50, nullable = false)
	private String brand;

	@Column(nullable = false)
	private Long price;

	@Column(name = "is_checked_delivery_fee", nullable = false)
	private boolean isCheckedDeliveryFee;

	@Column(length = 255, nullable = false)
	private String content;

	@Column(name = "purchase_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
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
	private LocalDate createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private LocalDate updateDate;

}
