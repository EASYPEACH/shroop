package com.easypeach.shroop.modules.product.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.domain.ProductGrade;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.product.dto.request.SearchRequest;
import com.easypeach.shroop.modules.product.dto.response.ProductOneImgResponse;
import com.easypeach.shroop.modules.product.dto.response.ProductResponse;
import com.easypeach.shroop.modules.product.dto.response.SearchProductResponse;
import com.easypeach.shroop.modules.product.service.ProductImgService;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends ControllerTest {

	@MockBean
	private ProductService productService;

	@MockBean
	private ProductImgService productImgService;

	@DisplayName("특정 상품을 조회한다")
	@Test
	void getProduct() throws Exception {
		Member member = new Member();
		ProductResponse.CategoryResponse categoryResponse = new ProductResponse.CategoryResponse(1L,"전자제품");
		ProductResponse.MemberResponse memberResponse = new ProductResponse.MemberResponse(
			1L,
			"판매자"
		);
		List<ProductResponse.ProductImgResponse> productImgResponseList = new ArrayList<>();
		ProductResponse.ProductImgResponse productImgResponse = ProductResponse.ProductImgResponse.builder()
			.id(1L)
			.productImgUrl("imgUrl")
			.isDefect(true)
			.build();
		productImgResponseList.add(productImgResponse);

		ProductResponse productResponse = ProductResponse.builder()
			.id(1L)
			.seller(memberResponse)
			.transactionStatus(TransactionStatus.PURCHASE_REQUEST)
			.title("중고물건 팔아요")
			.category(categoryResponse)
			.productGrade(ProductGrade.LOWER)
			.productImgList(productImgResponseList)
			.likesCount(0L)
			.isLike(false)
			.brand("애플/아이폰5")
			.price(10_000L)
			.isCheckedDeliveryFee(false)
			.content("제곧네제곧네제곧네제곧네제곧네제곧네제곧네제곧네제곧네제곧네제곧네")
			.isDefect(Boolean.FALSE)
			.saleReason("안써요안써요안써요")
			.purchaseDate(LocalDate.now())
			.createDate(LocalDateTime.now())
			.build();

		given(productService.getProductInfo(any(), any())).willReturn(productResponse);

		String memberJson = objectMapper.writeValueAsString(member);
		String productIdJson = objectMapper.writeValueAsString(1L);

		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/products/{productId}", 1L)
				.content(memberJson)
				.content(productIdJson)
			)
			.andExpect(status().isOk())
			.andDo(document("getOneProduct",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("특정 상품 참조키")
				), responseFields(
					fieldWithPath("id").description("상품 아이디"),
					fieldWithPath("seller").description("상품 판매자"),
					fieldWithPath("seller.id").description("판매자 아이디"),
					fieldWithPath("seller.nickName").description("판매자 닉네임"),
					fieldWithPath("transactionStatus").description("상품 거래 상태"),
					fieldWithPath("title").description("상품 제목"),
					fieldWithPath("category").description("상품 카테고리"),
					fieldWithPath("category.id").description("상품 카테고리 아이디"),
					fieldWithPath("category.name").description("상품 카테고리 이름"),
					fieldWithPath("productGrade").description("상품 등급"),
					fieldWithPath("productImgList").description("상품 이미지 리스트"),
					fieldWithPath("productImgList[].id").description("상품 이미지 아이디"),
					fieldWithPath("productImgList[].productImgUrl").description("상품 이미지 url"),
					fieldWithPath("productImgList[].isDefect").description("상품 결함 이미지 여부"),
					fieldWithPath("likesCount").description("상품 좋아요 총 수"),
					fieldWithPath("isLike").description("로그인 한 사용자의 좋아요 여부"),
					fieldWithPath("brand").description("상품 모델 브랜드명"),
					fieldWithPath("price").description("상품 가격"),
					fieldWithPath("isCheckedDeliveryFee").description("상품 배송비 포함 여부"),
					fieldWithPath("content").description("상품 상세 조건"),
					fieldWithPath("isDefect").description("상품 결함 여부"),
					fieldWithPath("saleReason").description("상품 판매 이유"),
					fieldWithPath("purchaseDate").description("구매 날짜"),
					fieldWithPath("createDate").description("상품 등록 날짜")
				)))
			.andDo(print());
		;

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
			"안써서 팔아요. 안써서 팔아요",
			"대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자"
		);

		List<MultipartFile> multipartFileList = new ArrayList<>();
		MockMultipartFile file1 = new MockMultipartFile("productImgList", "test1.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test1 image".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("defectImgList", "test2.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test2 image".getBytes());

		multipartFileList.add(file1);
		multipartFileList.add(file2);

		doNothing().when(productImgService).checkImgLength(multipartFileList);
		given(productService.saveProduct(1L, productRequest)).willReturn(1L);
		doNothing().when(productImgService).saveProductImg(multipartFileList, multipartFileList, 1L, true);

		String json = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(multipart("/api/products")
				.file(file1)
				.file(file2)
				.file(
					new MockMultipartFile("productRequest", "", "application/json",
						json.getBytes(StandardCharsets.UTF_8)))
				.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isOk())
			.andDo(document("saveProduct",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestParts(
					partWithName("productImgList").description("상품 이미지 리스트"),
					partWithName("defectImgList").description("상품 결함 이미지 리스트"),
					partWithName("productRequest").description("상품 등록 조건")
				),
				requestPartFields("productRequest",
					fieldWithPath("title").description("상품 제목"),
					fieldWithPath("categoryId").description("상품 카테고리 아이디"),
					fieldWithPath("brand").description("상품 브랜드 모델명"),
					fieldWithPath("price").description("상품 가격"),
					fieldWithPath("isCheckedDeliveryFee").description("상품 배송비 포함 여부"),
					fieldWithPath("purchaseDate").description("상품 구매 날짜"),
					fieldWithPath("productGrade").description("상품 등급"),
					fieldWithPath("isDefect").description("상품 결함 여부"),
					fieldWithPath("saleReason").description("상품 판매 이유"),
					fieldWithPath("content").description("상품 상세 조건")
				),
				responseFields(
					fieldWithPath("productId").description("상품 아이디")
				)))
			.andDo(print())
		;

	}

	@DisplayName("상품을 수정한다")
	@Test
	void updateProduct() throws Exception {
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
			"안써서 팔아요. 안써서 팔아요",
			"대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자"
		);

		List<MultipartFile> multipartFileList = new ArrayList<>();
		MockMultipartFile file1 = new MockMultipartFile("productImgList", "test1.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test1 image".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("defectImgList", "test2.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test2 image".getBytes());

		multipartFileList.add(file1);
		multipartFileList.add(file2);

		doNothing().when(productImgService).checkImgLength(multipartFileList);
		given(productService.saveProduct(1L, productRequest)).willReturn(1L);
		doNothing().when(productImgService).updateProductImgList(multipartFileList, multipartFileList, 1L, true);

		String productRequestJson = objectMapper.writeValueAsString(productRequest);
		String productIdJson = objectMapper.writeValueAsString(1L);

		mockMvc.perform(
				multipart("/api/products")
					.file(
						new MockMultipartFile("productId", "", "application/json",
							productIdJson.getBytes(StandardCharsets.UTF_8)))
					.file(file1)
					.file(file2)
					.file(
						new MockMultipartFile("productRequest", "", "application/json",
							productRequestJson.getBytes(StandardCharsets.UTF_8)))
					.contentType(MediaType.MULTIPART_FORM_DATA).with(request -> {
						request.setMethod("PATCH");
						return request;
					}))
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document("updateProduct",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestParts(
					partWithName("productId").description("수정 할 상품 아이디"),
					partWithName("productImgList").description("상품 이미지 리스트"),
					partWithName("defectImgList").description("상품 결함 이미지 리스트").optional(),
					partWithName("productRequest").description("상품 등록 조건")
				),
				requestPartFields("productRequest",
					fieldWithPath("title").description("상품 제목"),
					fieldWithPath("categoryId").description("상품 카테고리 아이디"),
					fieldWithPath("brand").description("상품 브랜드 모델명"),
					fieldWithPath("price").description("상품 가격"),
					fieldWithPath("isCheckedDeliveryFee").description("상품 배송비 포함 여부"),
					fieldWithPath("purchaseDate").description("상품 구매 날짜"),
					fieldWithPath("productGrade").description("상품 등급"),
					fieldWithPath("isDefect").description("상품 결함 여부"),
					fieldWithPath("saleReason").description("상품 판매 이유"),
					fieldWithPath("content").description("상품 상세 조건")
				),
				responseFields(
					fieldWithPath("productId").description("상품 아이디")
				)))
			.andDo(print())
		;

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
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestParameters(
					parameterWithName("title").description("검색 제목"),
					parameterWithName("categoryId").description("선택한 카테고리"),
					parameterWithName("hasNotTransaction").description("판매중 / 거래완료 필터"),
					parameterWithName("page").description("검색 페이지"),
					parameterWithName("size").description("페이징 단위 수"),
					parameterWithName("sort").description("정렬 기준")
				),
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
			.andDo(print())
		;

	}
}