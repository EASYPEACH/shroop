package com.easypeach.shroop.modules.transaction.controller;

import static com.easypeach.shroop.modules.transaction.domain.TransactionStatus.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.transaction.dto.response.HistoryResponse;
import com.easypeach.shroop.modules.transaction.dto.response.PageResponse;
import com.easypeach.shroop.modules.transaction.service.TransactionService;

@WebMvcTest(TransactionHistoryController.class)
class TransactionHistoryControllerTest extends ControllerTest {
	@MockBean
	TransactionService transactionService;

	@MockBean
	Page page;

	@Test
	@DisplayName("구매내역 조회 테스트 코드")
	void getBuyingHistory() throws Exception {
		List<HistoryResponse> dtoList = new ArrayList<>();
		LocalDateTime time = LocalDateTime.of(2000, 1, 1, 1, 1, 1, 0);
		HistoryResponse dto = new HistoryResponse(1L, time,
			PURCHASE_REQUEST, "상품 제목", 100000L,
			"상품 이미지 URL");
		dtoList.add(dto);
		int pageCount = 7;
		Page<HistoryResponse> page = new PageImpl<>(dtoList);
		PageResponse pageResponse = PageResponse.createPageResponse(7, page.getContent());

		given(transactionService.findAllBuyingHistory(any(), any())).willReturn(pageResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/history/buying/")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].id").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].transactionCreateDate")
				.value(time.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].transactionStatus")
				.value(PURCHASE_REQUEST.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].title").value("상품 제목"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].price").value(100000L))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].productImgUrl")
				.value("상품 이미지 URL"))
			.andDo(document("getBuyingHistory",
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("pageCount").description("총 페이지 수"),
					fieldWithPath("historyResponseList[].id").description("결제 아이디"),
					fieldWithPath("historyResponseList[].transactionCreateDate").description("거래 날짜"),
					fieldWithPath("historyResponseList[].transactionStatus").description("거래 상태"),
					fieldWithPath("historyResponseList[].title").description("상품 제목"),
					fieldWithPath("historyResponseList[].price").description("상품 가격"),
					fieldWithPath("historyResponseList[].productImgUrl").description("상품 이미지")
				)))
			.andDo(print());

	}

	@Test
	@DisplayName("판매내역 조회 테스트 코드")
	void getSellingHistory() throws Exception {
		List<HistoryResponse> dtoList = new ArrayList<>();
		LocalDateTime time = LocalDateTime.of(2000, 1, 1, 1, 1, 1, 0);
		HistoryResponse dto = new HistoryResponse(1L, time,
			PURCHASE_REQUEST, "상품 제목", 100000L,
			"상품 이미지 URL");
		dtoList.add(dto);
		int pageCount = 7;
		Page<HistoryResponse> page = new PageImpl<>(dtoList);
		PageResponse pageResponse = PageResponse.createPageResponse(7, page.getContent());
		given(transactionService.findAllSellingHistory(any(), any())).willReturn(pageResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/history/selling")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].id").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].transactionCreateDate")
				.value(time.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].transactionStatus")
				.value(PURCHASE_REQUEST.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].title").value("상품 제목"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].price").value(100000L))
			.andExpect(MockMvcResultMatchers.jsonPath("$.historyResponseList[0].productImgUrl")
				.value("상품 이미지 URL"))
			.andDo(document("getSellingHistory",
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("pageCount").description("총 페이지 수"),
					fieldWithPath("historyResponseList[].id").description("결제 아이디"),
					fieldWithPath("historyResponseList[].transactionCreateDate").description("거래 날짜"),
					fieldWithPath("historyResponseList[].transactionStatus").description("거래 상태"),
					fieldWithPath("historyResponseList[].title").description("상품 제목"),
					fieldWithPath("historyResponseList[].price").description("상품 가격"),
					fieldWithPath("historyResponseList[].productImgUrl").description("상품 이미지")
				)))
			.andDo(print());
	}
}