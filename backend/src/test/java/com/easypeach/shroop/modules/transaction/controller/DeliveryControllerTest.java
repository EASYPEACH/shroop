package com.easypeach.shroop.modules.transaction.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.transaction.dto.request.DeliveryRequest;
import com.easypeach.shroop.modules.transaction.service.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest extends ControllerTest {

	@MockBean
	private DeliveryService deliveryService;

	@Autowired
	ObjectMapper objectMapper;

	@DisplayName("택배 등록")
	@Test
	void saveDelivery() throws Exception {
		Long productId = 1L;
		DeliveryRequest deliveryRequest = new DeliveryRequest(
			"1234567890",
			"택배사명"
		);
		String json = objectMapper.writeValueAsString(deliveryRequest);

		doNothing().when(deliveryService).saveDelivery(productId, deliveryRequest);

		mockMvc.perform(post("/api/delivery/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
			.andDo(document("delivery",
				requestFields(
					fieldWithPath("trackingNumber").description("택배 송장번호"),
					fieldWithPath("parcel").description("택배사명")
				),
				responseFields(
					fieldWithPath("message").description("결과 메세지")
				)))
			.andDo(print());

	}

	@DisplayName("운송장번호 중복 체크")
	@Test
	void duplicateCheckTrackingNumber() throws Exception {
		String trackingNumber = "123123123";

		given(deliveryService.duplicateCheckTrackingNumber(trackingNumber)).willReturn(true);

		mockMvc.perform(get("/api/delivery/duplicate")
				.param("trackingNumber", trackingNumber))
			.andExpect(status().isOk())
			.andDo(document("duplicatTtrackingNumber",
				responseFields(
					fieldWithPath("result").description("중복 여부"),
					fieldWithPath("message").description("결과 메세지")
				)))
			.andDo(print());
	}
}