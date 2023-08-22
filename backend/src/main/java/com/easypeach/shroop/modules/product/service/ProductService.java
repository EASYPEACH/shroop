package com.easypeach.shroop.modules.product.service;

import org.springframework.stereotype.Service;

import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	public Product findByProductId(Long productId) {
		return productRepository.findById(productId).get();
	}

}
