package com.easypeach.shroop.modules.product.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductGrade;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.product.dto.request.SearchRequest;
import com.easypeach.shroop.modules.product.dto.response.ProductOneImgResponse;
import com.easypeach.shroop.modules.product.dto.response.ProductResponse;
import com.easypeach.shroop.modules.product.dto.response.SearchProductResponse;
import com.easypeach.shroop.modules.product.service.ProductImgService;
import com.easypeach.shroop.modules.product.service.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends ControllerTest {

	@MockBean
	private ProductService productService;

	@MockBean
	private ProductImgService productImgService;

	@DisplayName("상품 전체 리스트를 조회한다")
	@Test
	void getAllProduct() throws Exception {
		Member member = new Member();
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
		List<ProductResponse> productResponseList = new ArrayList<>();
		Product product = Product.forTestCreateProduct(productRequest);
		ProductResponse productResponse = productService.setProductResponse(product);
		productResponseList.add(productResponse);
		productResponseList.add(productResponse);

		given(productService.findAll(member)).willReturn(productResponseList);

		String json = objectMapper.writeValueAsString(member);
		mockMvc.perform(get("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk());
		// .andDo(document("getAllProduct", responseFields(
		// 	fieldWithPath("[].id").description("상품 아이디"),
		// 	fieldWithPath("[].seller").description("판매자 아이디"),
		// 	fieldWithPath("[].transactionStatus").description("거래 아이디"),
		// 	fieldWithPath("[].title").description("상품 제목"),
		// 	fieldWithPath("[].category").description("카테고리 아이디"),
		// 	fieldWithPath("[].productGrade").description("상품 등급"),
		// 	fieldWithPath("[].productImgList").description("상품 이미지 리스트"),
		// 	fieldWithPath("[].likesCount").description("좋아요 수"),
		// 	fieldWithPath("[].isLike").description("좋아요 여부"),
		// 	fieldWithPath("[].brand").description("상품 브랜드/모델명"),
		// 	fieldWithPath("[].price").description("상품 가격"),
		// 	fieldWithPath("[].isCheckedDeliveryFee").description("배달비 포함 여부"),
		// 	fieldWithPath("[].content").description("상품 상세 정보"),
		// 	fieldWithPath("[].isDefect").description("상품 결함 여부"),
		// 	fieldWithPath("[].saleReason").description("상품 판매 이유")
		// )));

	}

	@DisplayName("특정 상품을 조회한다")
	@Test
	void getProduct() throws Exception {
		Member member = new Member();
		Long productId = 1L;
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
		Product product = Product.forTestCreateProduct(productRequest);
		ProductResponse productResponse = productService.setProductResponse(product);

		given(productService.getProductInfo(member, productId)).willReturn(productResponse);

		String memberJson = objectMapper.writeValueAsString(member);
		String productIdJson = objectMapper.writeValueAsString(productId);

		mockMvc.perform(get("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(memberJson)
				.content(productIdJson)
			)
			.andExpect(status().isOk());

	}

	@DisplayName("상품을 등록한다")
	@Test
	void saveProduct() throws Exception {
		Member member = new Member();
		ProductRequest productRequest = new ProductRequest(
			"아이패드 5",
			1L,
			"애플",
			20000L,
			false,
			LocalDate.parse("2023-05-05"),
			ProductGrade.LOWER,
			false,
			"안써서 안팔아요. 안써서 안팔아요",
			"대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자"
		);
		Product product = Product.forTestCreateProduct(productRequest);

		List<MultipartFile> multipartFileList = new ArrayList<>();
		MockMultipartFile file1 = new MockMultipartFile("productImgList", "test1.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test1 image".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("defectImgList", "test2.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test2 image".getBytes());

		multipartFileList.add(file1);
		multipartFileList.add(file2);
		given(productService.saveProduct(1L, productRequest)).willReturn(product);

		String json = objectMapper.writeValueAsString(productRequest);
		String memberJson = objectMapper.writeValueAsString(member);
		mockMvc.perform(multipart("/api/products")
				.file(
					new MockMultipartFile("member", "", "application/json",
						memberJson.getBytes(StandardCharsets.UTF_8)))
				.file(file1)
				.file(file2)
				.file(
					new MockMultipartFile("productRequest", "", "application/json",
						json.getBytes(StandardCharsets.UTF_8)))
				.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isOk());

	}

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

		mockMvc.perform(get("/api/products/search")
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
					fieldWithPath("productList[].like").description("좋아요 여부"),
					fieldWithPath("productList[].transactionStatus").description("거래 상태")

				)));

	}
}