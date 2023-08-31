package com.easypeach.shroop.modules.likes.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.likes.service.LikeService;
import com.easypeach.shroop.modules.member.domain.Member;

@WebMvcTest(LikeController.class)
class LikeControllerTest extends ControllerTest {

	@MockBean
	private LikeService likeService;

	@Test
	@DisplayName("상품 좋아요 테스트 코드")
	void saveLikes() throws Exception {
		Long productId = 1L;

		SignUpRequest signUpRequest = new SignUpRequest("abc123456"
			, "abc123456!"
			, "abc123456"
			, "01000001111"
			, true
			, true
			, true
			, 1L
			, "01012341234");
		Member member = authService.saveMember(signUpRequest);

		given(likeService.saveLikes(member, productId)).willReturn(1L);

		mockMvc.perform(post("/api/likes/1", productId))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("상품 좋아요 취소 테스트 코드")
	void deleteLikes() throws Exception {
		Long productId = 1L;

		SignUpRequest signUpRequest = new SignUpRequest("abc123456"
			, "abc123456!"
			, "abc123456"
			, "01000001111"
			, true
			, true
			, true
			, 1L
			, "01012341234");
		Member member = authService.saveMember(signUpRequest);

		given(likeService.saveLikes(member, productId)).willReturn(1L);

		mockMvc.perform(delete("/api/likes/1", productId))
			.andExpect(status().isOk())
			.andDo(print());
	}
}