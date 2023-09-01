package com.easypeach.shroop.modules.product.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.easypeach.shroop.modules.product.dto.response.ProductOneImgResponse;

public interface ProductRepositoryCustom {
	Page<ProductOneImgResponse> searchProduct(String title, Long categoryId, boolean hasNotTransaction,
		Pageable pageable);
}
