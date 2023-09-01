package com.easypeach.shroop.modules.member.dto.reponse;

import com.easypeach.shroop.modules.likes.domain.Likes;
import com.easypeach.shroop.modules.product.domain.Product;

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
	private String productPrice;

	public LikeProductInfo(Likes likes){
		Product product = likes.getProduct();
		productId = product.getId();
		productImgUrl = product.getProductImgList().get(0).getProductImgUrl();
		productName = product.getTitle();
		productPrice = String.format("%,d",product.getPrice());

	}
}