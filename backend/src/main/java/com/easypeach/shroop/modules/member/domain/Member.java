package com.easypeach.shroop.modules.member.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.easypeach.shroop.modules.member.dto.request.ProfileEditRequest;
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

	private String profileImg;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Column(name = "login_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime loginDate;

	@Column(nullable = false)
	private Long point;

	@ColumnDefault("0")
	@Column(name = "grade_score")
	private Long gradeScore;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private LocalDateTime updateDate;

	@Column(nullable = false, name = "check_agree")
	private boolean checkAgree;

	@Column
	private String account;

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
		member.gradeScore = 0L;
		member.checkAgree = true;
		member.profileImg = "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/basicProfile.jpeg";
		return member;
	}

	public void updatePoint(Long updatedPoint) {
		this.point = updatedPoint;
	}

	public void subtractPoint(Long point) {
		this.point -= point;
	}

	public void addPoint(Long point) {
		this.point += point;
	}

	public void updateRole(Role role) {
		this.role = role;
	}

	public void updateAccount(String account) {
		this.account = account;
	}

	public void updateProfile(ProfileEditRequest profileEditRequest) {
		this.nickname = profileEditRequest.getNickname();
		this.password = profileEditRequest.getNewPassword();
		this.phoneNumber = profileEditRequest.getPhoneNumber();
	}

	public void addGradeScore(Long addScore) {
		this.gradeScore += addScore;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateProfileImg(String imgUrl) {
		this.profileImg = imgUrl;
	}

	public void updatePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

