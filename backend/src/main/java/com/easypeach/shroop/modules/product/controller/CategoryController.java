package com.easypeach.shroop.modules.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.dto.request.CategoryRequest;
import com.easypeach.shroop.modules.product.dto.response.CategoryResponse;
import com.easypeach.shroop.modules.product.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/categorys")
@RestController
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getCategoryList() {
		List<CategoryResponse> categoryResponse = categoryService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategory(@PathVariable final Long categoryId) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(categoryId));
	}

	@PostMapping
	public ResponseEntity<CategoryResponse> saveCategory(@RequestBody final CategoryRequest categoryRequest) {
		Category category = categoryService.saveCategory(categoryRequest.getName());
		return ResponseEntity.status(HttpStatus.OK).body(new CategoryResponse(category.getId(), category.getName()));
	}
}
