package com.easypeach.shroop.modules.product.service;

import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.respository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveCategory(String categoryName) {
        Category category = Category.createCategory(categoryName);
        return categoryRepository.save(category);
    }

}
