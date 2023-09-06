package com.easypeach.shroop.modules.product.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.dto.request.SearchRequest;
import com.easypeach.shroop.modules.product.dto.response.ProductOneImgResponse;
import com.easypeach.shroop.modules.product.dto.response.SearchProductResponse;
import com.easypeach.shroop.modules.product.service.ProductImgService;
import com.easypeach.shroop.modules.product.service.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends ControllerTest {

	@MockBean
	private ProductService productService;

	@MockBean
	private ProductImgService productImgService;

	@DisplayName("상품 검색 기능")
	@Test
	void searchProduct() throws Exception {
		Member member = new Member();
		SearchRequest searchRequest = new SearchRequest(
			"제목 검색",
			1L,
			false
		);
		int pageCount = 1;
		List<ProductOneImgResponse> productList = new ArrayList<>();
		productList.add(new ProductOneImgResponse());
		Page<ProductOneImgResponse> page = new PageImpl<>(productList);

		PageRequest pageable = PageRequest.of(0, 9, Sort.by(Sort.Direction.DESC, "createDate"));

		given(productService.searchProduct(any(), eq("제목 검색"), eq(1L), eq(false), any()))
			.willReturn(new SearchProductResponse(pageCount, page.getContent()));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/products/search")
				.param("title", searchRequest.getTitle())
				.param("categoryId", String.valueOf(searchRequest.getCategoryId()))
				.param("hasNotTransaction", String.valueOf(searchRequest.getHasNotTransaction()))
				.param("page", String.valueOf(pageable.getPageNumber()))
				.param("size", String.valueOf(pageable.getPageSize()))
				.param("sort", pageable.getSort().toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("searchProduct",
				responseFields(
					fieldWithPath("pageCount").description("총 페이지 수"),
					fieldWithPath("productList[].id").description("상품 아이디"),
					fieldWithPath("productList[].title").description("상품 제목"),
					fieldWithPath("productList[].categoryName").description("상품 카테고리명"),
					fieldWithPath("productList[].productImgUrl").description("상품 이지미 주소"),
					fieldWithPath("productList[].likesCount").description("상품 좋아요 개수"),
					fieldWithPath("productList[].price").description("상품 가격"),
					fieldWithPath("productList[].isCheckedDeliveryFee").description("상품 배송비 포함 여부"),
					fieldWithPath("productList[].content").description("상품 내용"),
					fieldWithPath("productList[].createDate").description("상품 생성일"),
					fieldWithPath("productList[].isLike").description("좋아요 여부"),
					fieldWithPath("productList[].transactionStatus").description("거래 상태")

				)))
			.andDo(print());

	}
}