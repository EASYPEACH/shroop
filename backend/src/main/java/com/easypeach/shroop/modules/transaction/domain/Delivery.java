package com.easypeach.shroop.modules.transaction.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tracking_number", unique = true, length = 255, nullable = false)
	private String trackingNumber;

	@Column(name = "parcel", length = 255, nullable = false)
	private String parcel;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	public static Delivery createDelivery(final String trackingNumber, final String parcel) {
		Delivery delivery = new Delivery();
		delivery.trackingNumber = trackingNumber;
		delivery.parcel = parcel;

		return delivery;
	}
}
