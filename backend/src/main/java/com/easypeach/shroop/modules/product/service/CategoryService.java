package com.easypeach.shroop.modules.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.dto.response.CategoryResponse;
import com.easypeach.shroop.modules.product.respository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Transactional
	public Category saveCategory(String categoryName) {
		Category category = Category.createCategory(categoryName);
		return categoryRepository.save(category);
	}

	public List<CategoryResponse> findAll() {
		return categoryRepository.findAll()
			.stream()
			.map(category -> new CategoryResponse(category.getId(), category.getName()))
			.collect(Collectors.toList());
	}

	public Category findById(Long id) {
		return categoryRepository.getById(id);
	}

}
