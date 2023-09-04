package com.easypeach.shroop.modules.member.domain;

import lombok.Getter;

@Getter
public enum ShroopMember {
	SHROOP_ID(1L);
	private Long id;

	ShroopMember(Long id) {
		this.id = id;
	}

}
