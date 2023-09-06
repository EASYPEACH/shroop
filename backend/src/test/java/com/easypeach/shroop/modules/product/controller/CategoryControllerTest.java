package com.easypeach.shroop.modules.product.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.dto.response.CategoryResponse;
import com.easypeach.shroop.modules.product.service.CategoryService;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest extends ControllerTest {

	@MockBean
	private CategoryService categoryService;

	@DisplayName("카테고리 리스트를 조회한다")
	@Test
	void getCategoryList() throws Exception {
		List<CategoryResponse> categoryResponseList = new ArrayList<>();
		categoryResponseList.add(new CategoryResponse(1L, "전자제품"));
		categoryResponseList.add(new CategoryResponse(2L, "가구"));

		given(categoryService.findAll()).willReturn(categoryResponseList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/categorys"))
			.andExpect(status().isOk())
			.andDo(document("getCategoryList", responseFields(
				fieldWithPath("[].id").description("카테고리 아이디"),
				fieldWithPath("[].name").description("카테고리 이름")
			)))
			.andDo(print());

	}

	@DisplayName("특정 카테고리를 조회한다")
	@Test
	void getCategory() throws Exception {
		Category category = new Category(1L, "전자제품");
		given(categoryService.findById(category.getId())).willReturn(category);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/categorys/1"))
			.andExpect(status().isOk())
			.andDo(document("getCategory", responseFields(
				fieldWithPath("id").description("카테고리 아이디"),
				fieldWithPath("name").description("카테고리 이름")
			))).andDo(print());
	}

}



