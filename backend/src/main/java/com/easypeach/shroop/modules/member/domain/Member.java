package com.easypeach.shroop.modules.member.domain;

import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "login_id", length = 50, nullable = false, unique = true)
	private String loginId;

	@Column(nullable = false)
	private String password;

	@Column(length = 50, nullable = false, unique = true)
	private String nickname;

	@Column(name = "phone_number", length = 50, nullable = false, unique = true)
	private String phoneNumber;

	@Column(name = "phone_auth_token")
	private String phoneAuthToken;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Column(name = "login_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime loginDate;

	@Column(nullable = false)
	private Long point;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private LocalDateTime updateDate;

	@Column(nullable = false, name = "check_agree")
	private boolean checkAgree;

	public static Member createMember(String loginId,
		String password,
		String nickname,
		String phoneNumber,
		Role role,
		Long point) {
		Member member = new Member();
		member.loginId = loginId;
		member.password = password;
		member.nickname = nickname;
		member.phoneNumber = phoneNumber;
		member.role = role;
		member.point = point;
		member.createRandomToken();
		member.checkAgree = true;
		return member;
	}

	public void updateMember(Long updatedPoint) {
		this.point = updatedPoint;
	}

	public void createRandomToken() {
		Random random = new Random();
		this.phoneAuthToken = String.format("%04d", random.nextInt(10000));
	}

	public void updateRole(Role role) {
		this.role = role;
	}

}

