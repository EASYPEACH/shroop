package com.easypeach.shroop.modules.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.domain.ProductStatus;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.product.exception.ProductDeleteException;
import com.easypeach.shroop.modules.product.respository.CategoryRepository;
import com.easypeach.shroop.modules.product.respository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final ProductImgService productImgService;

	public Product findByProductId(Long productId) {
		Product product = productRepository.findById(productId).get();
		return product;
	}

	public ProductImg getProductImg(Product product) {
		Product findProduct = productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException(""));
		findProduct.getProductImgList().get(0).getId();
		return findProduct.getProductImgList().get(0);
	}

	@Transactional
	public Product saveProduct(Long memberId, ProductRequest productRequest) {
		Member seller = memberRepository.findById(memberId).get();
		Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
		Product product = Product.createProduct(seller, productRequest, category);
		return productRepository.save(product);
	}

	@Transactional
	public Product updateProduct(Long productId, ProductRequest productRequest) {
		Product product = productRepository.findById(productId).get();
		Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
		product.updateProduct(productRequest, category);
		return product;
	}

	@Transactional
	public void deleteProduct(Long productId) {
		Product product = productRepository.findById(productId).get();
		if (product.getProductStatus() != ProductStatus.SELLING) {
			throw new ProductDeleteException(product.getProductStatus() + "상태에서는 삭제할 수 없습니다");
		} else {
			productRepository.delete(product);
		}
	}

}
