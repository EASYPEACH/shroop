package com.easypeach.shroop.modules.product.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.product.dto.request.ProductReturnRequest;
import com.easypeach.shroop.modules.product.service.ProductReturnService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductReturnController.class)
class ProductReturnControllerTest extends ControllerTest {

	@MockBean
	private ProductReturnService productReturnService;

	@DisplayName("반품 요청")
	@Test
	void saveProductReturn() throws Exception {
		Long memberId = 1L;
		Long productId = 1L;
		ProductReturnRequest productReturnRequest = new ProductReturnRequest("반품 신고 내용");
		MockMultipartFile file1 = new MockMultipartFile("productReturnImgList", "test1.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test1 image".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("productReturnImgList", "test2.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test2 image".getBytes());
		List<MultipartFile> productReturnImgList = new ArrayList<>();
		productReturnImgList.add(file1);
		productReturnImgList.add(file2);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(productReturnRequest);

		doNothing().when(productReturnService)
			.saveProductReturn(memberId, productId, productReturnRequest, productReturnImgList);

		mockMvc.perform(multipart("/api/return/{productId}", productId)
				.file(file1)
				.file(file2)
				.file(new MockMultipartFile("productReturnRequest", "", "application/json",
					json.getBytes(StandardCharsets.UTF_8)))
				.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isOk())
			.andDo(document("productReturn",
				responseFields(
					fieldWithPath("message").description("결과 메세지")
				)))
			.andDo(print());

	}
}