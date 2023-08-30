package com.easypeach.shroop.modules.transaction.controller;

import static com.easypeach.shroop.modules.transaction.domain.TransactionStatus.*;
import static org.mockito.BDDMockito.*;
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
		LocalDateTime time = LocalDateTime.of(2019, 11, 12, 12, 32, 22, 3333);
		HistoryResponse dto = new HistoryResponse(1L, time,
			PURCHASE_REQUEST, "아이패드", 30000L,
			"https://shroop-s3.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%AA%E1%86%AF.png");
		dtoList.add(dto);

		given(transactionService.findAllBuyingHistory(any())).willReturn(dtoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/buying/history")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionCreateDate")
				.value(time.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionStatus").value(PURCHASE_REQUEST.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("아이패드"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(30000L))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].productImgUrl")
				.value("https://shroop-s3.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%AA%E1%86%AF.png"))
			.andDo(print());

	}

	@Test
	void getSellingHistory() throws Exception {
		List<HistoryResponse> dtoList = new ArrayList<>();
		LocalDateTime time = LocalDateTime.of(2019, 11, 12, 12, 32, 22, 3333);
		HistoryResponse dto = new HistoryResponse(1L, time,
			PURCHASE_REQUEST, "아이패드", 30000L,
			"https://shroop-s3.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%AA%E1%86%AF.png");
		dtoList.add(dto);

		Page<HistoryResponse> page = new PageImpl<>(dtoList);

		given(transactionService.findAllSellingHistory(any(), any())).willReturn(page);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/selling/history")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionCreateDate")
				.value(time.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionStatus").value(PURCHASE_REQUEST.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("아이패드"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(30000L))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].productImgUrl")
				.value("https://shroop-s3.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%AA%E1%86%AF.png"))
			.andDo(print());
	}
}