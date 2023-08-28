package com.easypeach.shroop.modules.product.dto.response;

import com.easypeach.shroop.modules.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberResonse {
	private Long id;

	private String nickName;

	public MemberResonse(Member member) {
		this.id = member.getId();
		this.nickName = member.getNickname();
	}
}
