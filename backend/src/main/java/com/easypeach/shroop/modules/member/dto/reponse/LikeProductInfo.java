package com.easypeach.shroop.modules.member.dto.reponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.easypeach.shroop.modules.likes.domain.Likes;
import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeProductInfo {
	private Long id;
	private String productImgUrl;
	private String title;
	private String price;
	private Category category;
	private LocalDateTime createDate;

	public LikeProductInfo(Likes likes){
		Product product = likes.getProduct();
		id = product.getId();
		productImgUrl = product.getProductImgList().get(0).getProductImgUrl();
		title = product.getTitle();
		price = String.format("%,d",product.getPrice());
		category = product.getCategory();
		createDate = product.getCreateDate();

	}
}