package com.easypeach.shroop.modules.likes.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

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
		Member member = new Member();

		given(likeService.saveLikes(member, productId)).willReturn(1L);

		mockMvc.perform(RestDocumentationRequestBuilders.post("/api/likes/{productId}", productId))
			.andExpect(status().isOk())
			.andDo(document("likes",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("특정 상품 참조키")
				)
			))
			.andDo(print());
	}

	@Test
	@DisplayName("상품 좋아요 취소 테스트 코드")
	void deleteLikes() throws Exception {
		Long productId = 1L;
		Member member = new Member();

		given(likeService.saveLikes(member, productId)).willReturn(1L);

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/likes/{productId}", productId))
			.andExpect(status().isOk())
			.andDo(document("cancleLikes",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("productId").description("특정 상품 참조키")
				)
			))
			.andDo(print());
	}
}