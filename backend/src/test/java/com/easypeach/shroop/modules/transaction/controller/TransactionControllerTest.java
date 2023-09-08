package com.easypeach.shroop.modules.transaction.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.dto.request.TransactionCreateRequest;
import com.easypeach.shroop.modules.transaction.dto.response.BuyerResponse;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionCreatedResponse;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionInfoResponse;
import com.easypeach.shroop.modules.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest extends ControllerTest {

	@MockBean
	TransactionService transactionService;

	@MockBean
	ProductService productService;

	@Test
	@DisplayName("결제창 테스트 코드")
	void getBuyingForm() throws Exception {
		Long productId = 1L;

		TransactionInfoResponse dto = new TransactionInfoResponse("상품 이미지 URL", "상품 제목", 100000L, 100000L);

		given(transactionService.createTransactionInfoResponse(any(), any())).willReturn(dto);

		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/buying/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.productImgUrl").value("상품 이미지 URL"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("상품 제목"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100000L))
			.andExpect(MockMvcResultMatchers.jsonPath("$.point").value(100000L))
			.andDo(document("getBuyingForm",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("productImgUrl").description("상품 URL"),
					fieldWithPath("title").description("상품 제목"),
					fieldWithPath("price").description("상품 가격"),
					fieldWithPath("point").description("구매자의 포인트")
				))).andDo(print());

	}

	@Test
	@DisplayName("결제하기 테스트 코드")
	void buyingProduct() throws Exception {
		Long productId = 1L;

		doNothing().when(transactionService).createTransaction(any(), any(), any());

		ObjectMapper objectMapper = new ObjectMapper();
		TransactionCreateRequest transactionCreateRequest = new TransactionCreateRequest("구매자 이름", "01012345678",
			"구매자 주소");
		String json = objectMapper.writeValueAsString(transactionCreateRequest);

		mockMvc.perform(RestDocumentationRequestBuilders.post("/api/buying/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
			.andDo(document("buyingProduct",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 아이디")
				),
				requestFields(
					fieldWithPath("buyerName").description("구매자 이름"),
					fieldWithPath("buyerPhoneNumber").description("구매자 번호"),
					fieldWithPath("buyerLocation").description("구매자 주소")
				),
				responseFields(
					fieldWithPath("message").description("결제가 완료되었습니다.")
				)))
			.andDo(print());
	}

	@Test
	@DisplayName("결제 완료창 테스트 코드")
	void getBuyingCompletedForm() throws Exception {
		Long productId = 1L;
		TransactionCreatedResponse transactionCreatedResponse = new TransactionCreatedResponse(1L, "상품 이미지 URL",
			"상품 제목",
			100000L, "구매자 이름", "01012345678", "구매자 주소");

		given(transactionService.createTransactionCreatedResponse(any())).willReturn(transactionCreatedResponse);

		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/buying/completed/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("$.productImgUrl").value("상품 이미지 URL"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.productTitle").value("상품 제목"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(100000L))
			.andExpect(MockMvcResultMatchers.jsonPath("$.buyerName").value("구매자 이름"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.buyerPhoneNumber").value("01012345678"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.buyerLocation").value("구매자 주소"))
			.andDo(document("getBuyingCompletedForm",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("transactionId").description("거래 아이디"),
					fieldWithPath("productImgUrl").description("상품 이미지"),
					fieldWithPath("productTitle").description("상품 제목"),
					fieldWithPath("productPrice").description("상품 가격"),
					fieldWithPath("buyerName").description("구매자 이름"),
					fieldWithPath("buyerPhoneNumber").description("구매자 번호"),
					fieldWithPath("buyerLocation").description("구매자 주소")
				)))
			.andDo(print());

	}

	@DisplayName("구매자정보 조회")
	@Test
	void getBuyer() throws Exception {
		Long productId = 1L;
		BuyerResponse buyerResponse = new BuyerResponse("구매자 이름", "구매자 주소", "구매자 휴대번호");

		given(transactionService.getBuyer(productId)).willReturn(buyerResponse);

		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/buying/buyer/{productId}", productId)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("buyer",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("name").description("구매자 이름"),
					fieldWithPath("location").description("구매자 주소"),
					fieldWithPath("phoneNumber").description("구매자 휴대번호")
				)))
			.andDo(print());

	}

	@DisplayName("구매 취소")
	@Test
	void cancelTransaction() throws Exception {
		Long memberId = 2L;
		Long productId = 1L;

		doNothing().when(transactionService).cancelTransaction(memberId, productId);

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/buying/{productId}", productId))
			.andExpect(status().isOk())
			.andDo(document("cancelTransaction",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("message").description("결과 메세지")
				)))
			.andDo(print());
	}

	@DisplayName("구매 확정")
	@Test
	void purchaseConfirm() throws Exception {
		Long memberId = 1L;
		Long productId = 1L;

		doNothing().when(transactionService).purchaseConfirm(memberId, productId);

		mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/buying/confirm/{productId}", productId))
			.andExpect(status().isOk())
			.andDo(document("purchaseConfirm",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("message").description("결과 메세지")
				)))
			.andDo(print());
	}

	@DisplayName("반품 확정")
	@Test
	void returnConfirm() throws Exception {
		Long memberId = 1L;
		Long productId = 1L;

		doNothing().when(transactionService).returnConfirm(memberId, productId);

		mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/buying/return/confirm/{productId}", productId))
			.andExpect(status().isOk())
			.andDo(document("returnConfirm",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("message").description("결과 메세지")
				)))
			.andDo(print());
	}
}