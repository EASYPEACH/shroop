package com.easypeach.shroop.modules.product.controller;

import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.dto.response.CategoryResponse;
import com.easypeach.shroop.modules.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/categorys")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategoryList() {
        log.info("category service 확인");
        List<CategoryResponse> categoryResponse = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(categoryId));
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(String categoryName) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.saveCategory(categoryName));
    }
}
