package com.easypeach.shroop.modules.bank.controller;

import static org.mockito.Mockito.*;
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

import com.easypeach.shroop.modules.bank.dto.LinkBankRequest;
import com.easypeach.shroop.modules.common.ControllerTest;

@WebMvcTest(BankController.class)
class BankControllerTest extends ControllerTest {

	@Test
	@DisplayName("계좌 연동하기 테스트 코드")
	void linkAccount() throws Exception {
		LinkBankRequest dto = LinkBankRequest.dtoForTest();
		doNothing().when(bankService).linkingAccount(any(), any());

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/bank/linking")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
			.andDo(document("linkAccount",
				preprocessRequest(prettyPrint()),
				requestFields(
					fieldWithPath("name").description("이름"),
					fieldWithPath("account").description("계좌번호"),
					fieldWithPath("password").description("계좌 비밀번호")
				)))
			.andDo(print());

	}
}