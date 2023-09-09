package com.easypeach.shroop.modules.member.dto.reponse;

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
	private CategoryInfo categoryInfo;
	private LocalDateTime createDate;

	public LikeProductInfo(Likes likes) {
		Product product = likes.getProduct();
		id = product.getId();
		productImgUrl = product.getProductImgList().get(0).getProductImgUrl();
		title = product.getTitle();
		price = String.format("%,d", product.getPrice());
		categoryInfo = new CategoryInfo(product.getCategory());
		createDate = product.getCreateDate();

	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CategoryInfo {
		private Long id;
		private String name;

		public CategoryInfo(Category category) {
			id = category.getId();
			name = category.getName();
		}
	}
}