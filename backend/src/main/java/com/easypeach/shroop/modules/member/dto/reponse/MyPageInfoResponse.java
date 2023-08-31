package com.easypeach.shroop.modules.member.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageInfoResponse {
	private String userImg;
	private String nickname;
	private Long point;
}
