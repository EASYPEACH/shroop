package com.easypeach.shroop.modules.member.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.member.dto.reponse.PointResponse;
import com.easypeach.shroop.modules.member.dto.request.PointRequest;

@WebMvcTest(PointController.class)
class PointControllerTest extends ControllerTest {

	@Test
	@DisplayName("충전하기 테스트 코드")
	void chargePoint() throws Exception {
		PointRequest pointRequest = PointRequest.createPointRequest(100000L);
		PointResponse pointResponse = PointResponse.createPointResponse(100000L);

		given(memberService.chargePoint(any(), any())).willReturn(pointResponse);
		String json = objectMapper.writeValueAsString(pointRequest);

		mockMvc.perform(MockMvcRequestBuilders.patch("/api/point/charging")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk()) // HTTP 상태 코드가 OK(200)인지 확인
			.andExpect(MockMvcResultMatchers.jsonPath("$.point").value(100000L)) // 응답 본문이 updatedPoint와 일치하는지 확인
			.andDo(document("chargePoint",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("point").description("충전할 포인트")
				),
				responseFields(
					fieldWithPath("point").description(("총 보유 포인트")
					)
				)

			))
			.andDo(print());
	}

	@Test
	@DisplayName("환전하기 테스트 코드")
	void exchangePoint() throws Exception {
		PointRequest pointRequest = PointRequest.createPointRequest(100000L);
		PointResponse pointResponse = PointResponse.createPointResponse(100000L);

		given(memberService.exchangePoint(any(), any())).willReturn(pointResponse);

		String json = objectMapper.writeValueAsString(pointRequest);
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/point/exchanging")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.point").value(100000L))
			.andDo(document("exchangePoint",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("point").description("환전할 포인트")
				),
				responseFields(
					fieldWithPath("point").description("총 보유 포인트")
				)))
			.andDo(print())
		;

	}
}