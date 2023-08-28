package com.easypeach.shroop.modules.transaction.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.dto.response.BuyerResponse;
import com.easypeach.shroop.modules.transaction.service.TransactionService;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest extends ControllerTest {

	@MockBean
	TransactionService transactionService;

	@MockBean
	ProductService productService;

	@DisplayName("구매자정보 조회")
	@Test
	void getBuyer() throws Exception {
		Long productId = 1L;
		BuyerResponse buyerResponse = new BuyerResponse("구매자 이름", "구매자 주소", "구매자 휴대번호");

		given(transactionService.getBuyer(productId)).willReturn(buyerResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/buying/buyer/{productId}", productId)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("buyer",
				responseFields(
					fieldWithPath("name").description("구매자 이름"),
					fieldWithPath("location").description("구매자 주소"),
					fieldWithPath("phone_number").description("구매자 휴대번호")
				)))
			.andDo(print());

	}
}