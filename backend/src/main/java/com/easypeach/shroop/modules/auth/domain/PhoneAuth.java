package com.easypeach.shroop.modules.auth.domain;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class PhoneAuth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "phone_number", length = 50, nullable = false)
	private String phoneNumber;

	@Column(name = "auth_number", length = 4, nullable = false)
	private String authNumber;

	@Column(name = "exp_time")
	private Date expDate;

	@Column(name = "fail_count")
	private Integer failCount;

	@Column(name = "auth_result")
	private boolean authResult;

	public String createAuthNumber() {
		Random random = new Random();
		return String.format("%04d", random.nextInt(10000));
	}

	public static PhoneAuth createPhoneAuth(String phoneNumber, Date exp) {
		PhoneAuth phoneAuth = new PhoneAuth();
		phoneAuth.phoneNumber = phoneNumber;
		phoneAuth.authNumber = phoneAuth.createAuthNumber();
		phoneAuth.authResult = false;
		phoneAuth.expDate = exp;
		phoneAuth.failCount = 0;
		return phoneAuth;
	}

	public void updateAuthResult(boolean result) {
		this.authResult = result;
	}

	public void addFailCount() {
		this.failCount++;
	}
}
