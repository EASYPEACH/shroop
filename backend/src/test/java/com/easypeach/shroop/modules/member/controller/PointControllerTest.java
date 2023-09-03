package com.easypeach.shroop.modules.member.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.common.ControllerTest;

@WebMvcTest(PointController.class)
class PointControllerTest extends ControllerTest {

	@Test
	@DisplayName("충전하기 테스트 코드")
	void chargePoint() throws Exception {
		Long point = 10000L;
		Long updatedPoint = 60000L;

		doNothing().when(bankService).subtractMoney(eq(10000L), any());
		given(memberService.plusPoint(eq(10000L), any())).willReturn(updatedPoint);

		mockMvc.perform(MockMvcRequestBuilders.patch("/api/point/charging")
				.contentType(MediaType.APPLICATION_JSON)
				.content(point.toString()))
			.andExpect(status().isOk()) // HTTP 상태 코드가 OK(200)인지 확인
			.andExpect(content().string(updatedPoint.toString())); // 응답 본문이 updatedPoint와 일치하는지 확인

	}

	@Test
	@DisplayName("환전하기 테스트 코드")
	void exchangePoint() throws Exception {
		Long point = 10000L;
		Long updatedPoint = 60000L;

		given(memberService.subtractPoint(eq(10000L), any())).willReturn(updatedPoint);
		doNothing().when(bankService).addMoney(eq(10000L), any());

		mockMvc.perform(MockMvcRequestBuilders.patch("/api/point/exchanging")
				.contentType(MediaType.APPLICATION_JSON)
				.content(point.toString()))
			.andExpect(status().isOk())
			.andExpect(content().string(updatedPoint.toString()));

	}
}