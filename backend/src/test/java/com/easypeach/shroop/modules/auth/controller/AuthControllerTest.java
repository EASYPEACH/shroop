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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.auth.dto.request.PhoneAuthRequest;
import com.easypeach.shroop.modules.auth.dto.request.PhoneNumber;
import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.dto.response.LoginCheckResponse;
import com.easypeach.shroop.modules.auth.dto.response.PhoneAuthUUID;
import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.member.domain.Member;

import lombok.Getter;

class AuthControllerTest extends ControllerTest {

	@DisplayName("회원가입 - 1. 휴대전화인증을 진행한다")
	@Test
	void getAuthNumber() throws Exception {
		// given
		given(phoneAuthService.sendAuthNumber("01012341234")).willReturn(new PhoneAuthUUID(1L, 20L));
		PhoneNumber phoneAuthRequest = new PhoneNumber("01012341234");

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/phone")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(phoneAuthRequest)))
			.andDo(print())
			.andDo(document("auth/sign-up-1",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("auth/sign-up-1",
				requestFields(
					fieldWithPath("phoneNumber").description("휴대전화번호")
				),
				responseFields(
					fieldWithPath("uuid").description("쿠키에 저장할 UUID 전달"),
					fieldWithPath("seconds").description("인증 제한 시간 (초)")
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
			.andDo(document("auth/sign-up-2",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("auth/sign-up-2",
				requestFields(
					fieldWithPath("uuid").description("휴대 전화 인증 시, 전달받은 uuid "),
					fieldWithPath("phoneNumber").description("휴대전화번호"),
					fieldWithPath("phoneAuthNumber").description("인증번호4자리")
				),
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
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/sign-up")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest)))
			.andDo(print())
			.andDo(document("auth/sign-up-3",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("auth/sign-up-3",
				requestFields(
					fieldWithPath("loginId").description("로그인ID"),
					fieldWithPath("password").description("패스워드"),
					fieldWithPath("nickname").description("닉네임"),
					fieldWithPath("phoneNumber").description("휴대전화번호"),
					fieldWithPath("agreeShroop").description("약관 동의 여부"),
					fieldWithPath("agreePersonal").description("약관 동의 여부"),
					fieldWithPath("agreeIdentify").description("약관 동의 여부"),
					fieldWithPath("uuid").description("인증 요청시 받은 uuid"),
					fieldWithPath("phoneAuthNumber").description("인증번호4자리")
				),
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
			.andDo(document("auth/sign-up-valid",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().is4xxClientError())
			.andDo(document("auth/sign-up-valid",
				requestFields(
					fieldWithPath("loginId").description("로그인ID"),
					fieldWithPath("password").description("패스워드"),
					fieldWithPath("nickname").description("닉네임"),
					fieldWithPath("phoneNumber").description("휴대전화번호"),
					fieldWithPath("agreeShroop").description("약관 동의 여부"),
					fieldWithPath("agreePersonal").description("약관 동의 여부"),
					fieldWithPath("agreeIdentify").description("약관 동의 여부"),
					fieldWithPath("uuid").description("인증 요청시 받은 uuid"),
					fieldWithPath("phoneAuthNumber").description("인증번호4자리")
				),
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

	@DisplayName("로그인 여부 체크")
	@Test
	void login_Check() throws Exception {
		//given
		LoginCheckResponse loginCheckResponse = new LoginCheckResponse(1L, "loginId", "nickname", true);
		given(authService.checkLogin(any())).willReturn(loginCheckResponse);

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
			.andDo(print())
			.andDo(document("auth/check",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("auth/check",
				responseFields(
					fieldWithPath("memberId").description("멤버 UID"),
					fieldWithPath("loginId").description("로그인 아이디"),
					fieldWithPath("nickname").description("닉네임"),
					fieldWithPath("result").description("로그인 여부")
				)))
			.andReturn();
	}

}