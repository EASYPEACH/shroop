package com.easypeach.shroop.infra.aop.log.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.easypeach.shroop.modules.member.domain.Member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long memberId;

	@Column
	private String log;

	private String paramName;

	@Column(length = 10000)
	private String paramValue;

	private LocalDateTime createdTime;

	public static UserLog createUserLog(Long memberId,String log){
		UserLog userLog = new UserLog();
		userLog.memberId = memberId;
		userLog.log= log;
		userLog.createdTime = LocalDateTime.now();
		return userLog;
	}

	public void setParamName(String name){
		this.paramName = name;
	}
	public void setParamValue(String value){
		this.paramValue = value;
	}
}
