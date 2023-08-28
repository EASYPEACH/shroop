package com.easypeach.shroop.modules.auth.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.auth.dto.request.PhoneAuthRequest;
import com.easypeach.shroop.modules.auth.dto.request.PhoneNumber;
import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.member.domain.Member;

import lombok.Getter;

@MockBean(JpaMetamodelMappingContext.class)
class AuthControllerTest extends ControllerTest {

	@DisplayName("회원가입 - 1. 휴대전화인증을 진행한다")
	@Test
	void getAuthNumber() throws Exception {
		// given
		given(phoneAuthService.sendAuthNumber("01012341234")).willReturn(1L);
		PhoneNumber phoneAuthRequest = new PhoneNumber("01012341234");

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/phone")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(phoneAuthRequest)))
			.andDo(print())
			.andDo(document("auth/sign-up",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("auth/sign-up",
				responseFields(
					fieldWithPath("uuid").description("개인 UUID 전달")
				)))
			.andReturn();
	}

	@DisplayName("회원가입 - 2. 휴대 전화 인증을 완료한다")
	@Test
	void phoneAuth() throws Exception {
		// given
		PhoneAuthRequest phoneAuthRequest = new PhoneAuthRequest(1L, "01012341234", "1234");
		doNothing().when(phoneAuthService).checkPhoneAuthNumber(any());

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/check").with(csrf())
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(phoneAuthRequest)))
			.andDo(print())
			.andDo(document("auth/sign-up",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("auth/sign-up",
				responseFields(
					fieldWithPath("message").description("인증 성공 여부")
				)))
			.andReturn();
	}

	@DisplayName("회원가입 - 3. 인증 완료 후 회원 정보를 전달한다")
	@Test
	void signUp() throws Exception {
		// given
		SignUpRequest signUpRequest = new SignUpRequest("abc123456"
			, "abc123456!"
			, "abc123456"
			, "01000001111"
			, true
			, true
			, true
			, 1L
			, "01012341234");
		given(authService.saveMember(any(SignUpRequest.class))).willReturn(new Member());
		doNothing().when(phoneAuthService).checkPhoneAuthNumber(any());

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/sign-up").with(csrf())
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest)))
			.andDo(print())
			.andDo(document("auth/sign-up",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("auth/sign-up",
				responseFields(
					fieldWithPath("message").description("휴대전화 인증을 완료해주세요")
				)))
			.andReturn();
	}

	@DisplayName("회원가입 유효성 검사")
	@Test
	void signUp_Validation() throws Exception {
		// given
		SignUpRequest signUpRequest = new SignUpRequest("a5"
			, "abc123456!"
			, "abc123456"
			, "01000001111"
			, true
			, true
			, true
			, 1L
			, "01012341234");

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/sign-up").with(csrf())
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest)))
			.andDo(print())
			.andDo(document("auth/sign-up",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().is4xxClientError())
			.andDo(document("auth/sign-up",
				responseFields(
					fieldWithPath("message").description("유효성 검사 실패 사유")
				)))
			.andReturn();
	}

	@Getter
	static class PhoneAuth {
		public String loginId;
		public String phoneAuthNumber;

		public PhoneAuth(String loginId, String phoneAuthNumber) {
			this.loginId = loginId;
			this.phoneAuthNumber = phoneAuthNumber;
		}
	}

}