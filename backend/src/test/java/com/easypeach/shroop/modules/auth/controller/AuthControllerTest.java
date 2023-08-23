package com.easypeach.shroop.modules.auth.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.auth.dto.request.SignInRequest;
import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.common.SecurityControllerTest;

class AuthControllerTest extends SecurityControllerTest {

	@DisplayName("회원가입을 진행한다")
	@Test
	void signUp() throws Exception {
		// given
		SignUpRequest signUpRequest = new SignUpRequest("abc123456"
			, "abc123456!"
			, "abc123456"
			, "01000001111");

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
					fieldWithPath("message").description("회원가입 성공 여부")
				)))
			.andReturn();
	}

	@DisplayName("회원가입 유효성 검사")
	@Test
	void signUp_Validation() throws Exception {
		// given
		SignUpRequest signUpRequest = new SignUpRequest("abc"
			, "abc123456"
			, "abc123456"
			, "01000001111");

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

	@DisplayName("로그인을 진행한다")
	@Test
	void login() throws Exception {
		// given
		SignUpRequest signUpRequest = new SignUpRequest("abc12345"
			, "abc12345"
			, "abc12345"
			, "01000001111");
		authService.saveMember(signUpRequest);
		SignInRequest loginDto = new SignInRequest("abc12345", "abc12345");

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/sign-in").with(csrf())
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto)))
			.andDo(print())
			.andDo(document("auth/sign-in",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("auth/sign-in",
				responseFields(
					fieldWithPath("loginId").description("회원 로그인 아이디"),
					fieldWithPath("nickname").description("회원 닉네임")
				)))
			.andReturn();
	}

}