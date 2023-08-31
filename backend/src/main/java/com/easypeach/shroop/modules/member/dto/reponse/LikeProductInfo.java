package com.easypeach.shroop.modules.member.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeProductInfo {
	private Long productId;
	private String productImgUrl;
	private String productName;
	private Long productPrice;
}