package com.easypeach.shroop.infra.aop.log.user;

import javax.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserLogDto {
	private Long memberId;
	private String log;
}
