package com.easypeach.shroop.modules.product.service;

import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.dto.response.CategoryResponse;
import com.easypeach.shroop.modules.product.exception.CategoryNotFoundException;
import com.easypeach.shroop.modules.product.respository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<CategoryResponse> findAll() {
        log.info("categroy service");

        return categoryRepository.findAll().stream().map(category -> new CategoryResponse(category.getName())).collect(Collectors.toList());
    }

    @Transactional
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다"));
    }

}
