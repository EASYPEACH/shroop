package com.easypeach.shroop.infra.config;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.service.AuthService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductGrade;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.product.respository.ProductImgRepository;
import com.easypeach.shroop.modules.product.service.CategoryService;
import com.easypeach.shroop.modules.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDb {
	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.dbInit1();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {
		private final AuthService authService;
		private final CategoryService categoryService;
		private final ProductService productService;
		private final ProductImgRepository productImgRepository;
		private final String[] categoryList = {"가전제품", "의류", "생활용품", "컴퓨터/IT"};

		public void dbInit1() {
			// member
			SignUpRequest signUpRequest1 = new SignUpRequest("test11",
				"test11!",
				"test11",
				"01011111111");
			SignUpRequest signUpRequest2 = new SignUpRequest("test22",
				"test22!",
				"test22",
				"01022222222");
			authService.saveMember(signUpRequest1);
			authService.saveMember(signUpRequest2);

			// 카테고리
			for (String categoryName : categoryList) {
				categoryService.saveCategory(categoryName);
			}

			// 상품
			ProductRequest productRequest = new ProductRequest(
				"아이패드 5",
				1L,
				"애플",
				20000L,
				false,
				LocalDate.parse("2023-05-05"),
				ProductGrade.LOWER,
				true,
				"안써서 안팔아요. 안써서 안팔아요",
				"대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자"
			);

			productService.saveProduct(1L, productRequest);

			Product product = productService.findByProductId(1L);
			ProductImg productImg = ProductImg.createProductImg(product, "https://cdn.vuetifyjs.com/images/john.jpg",
				false);
			productImgRepository.save(productImg);

		}
	}

}