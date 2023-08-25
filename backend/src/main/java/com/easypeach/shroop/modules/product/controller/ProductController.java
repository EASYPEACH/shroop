package com.easypeach.shroop.modules.product.controller;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.product.dto.response.ProductCreatedResponse;
import com.easypeach.shroop.modules.product.service.CategoryService;
import com.easypeach.shroop.modules.product.service.ProductImgService;
import com.easypeach.shroop.modules.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductImgService productImgService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ProductCreatedResponse> saveProduct(
            @LoginMember Member member,
            @RequestPart(value = "productImgList") List<MultipartFile> productImgList,
            @RequestPart(value = "defectImgList", required = false) List<MultipartFile> defectImgList,
            @RequestPart ProductRequest productRequest
    ) {
        Category category = categoryService.saveCategory(productRequest.getCategoryName());
        Product product = productService.saveProduct(member.getId(), productRequest, category);
        productImgService.saveProductImg(product, productImgList);
        log.info("get brand {}", productRequest.getBrand());
        if (product.isDefect()) {
            productImgService.saveProductImg(product, defectImgList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ProductCreatedResponse(product.getId()));
    }
}
