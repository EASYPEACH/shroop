package com.easypeach.shroop.modules.transaction.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tracking_number", unique = true, nullable = false)
	private Long trackingNumber;
	
	@Column(name = "parcel", length = 255, nullable = false)
	private String parcel;
}
