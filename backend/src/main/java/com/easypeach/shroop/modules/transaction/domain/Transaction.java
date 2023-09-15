package com.easypeach.shroop.modules.transaction.domain;

import java.time.LocalDateTime;

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
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;

import lombok.Getter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false)
	private Member buyer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
	private Member seller;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false, unique = true)
	private Product product;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@Column(name = "buyer_name", length = 255, nullable = false)
	private String buyerName;

	@Column(name = "buyer_phone_number", length = 255, nullable = false)
	private String buyerPhoneNumber;

	@Column(name = "buyer_postcode", length = 255, nullable = false)
	private String buyerPostcode;

	@Column(name = "buyer_location", length = 255, nullable = false)
	private String buyerLocation;

	@Column(name = "buyer_detail_location", length = 255, nullable = false)
	private String buyerDetailLocation;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	public static Transaction createTransaction(final Member buyer, final Member seller, final Product product,
		final TransactionStatus status, final String buyerName, final String buyerPhoneNumber,
		final String buyerPostcode,
		final String buyerLocation, final String buyerDetailLocation
	) {
		Transaction transaction = new Transaction();
		transaction.buyer = buyer;
		transaction.seller = seller;
		transaction.product = product;
		transaction.status = status;
		transaction.buyerName = buyerName;
		transaction.buyerPhoneNumber = buyerPhoneNumber;
		transaction.buyerPostcode = buyerPostcode;
		transaction.buyerLocation = buyerLocation;
		transaction.buyerDetailLocation = buyerDetailLocation;

		return transaction;
	}

	public void updateStatus(TransactionStatus transactionStatus) {
		this.status = transactionStatus;
	}

	public void updateDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
}
