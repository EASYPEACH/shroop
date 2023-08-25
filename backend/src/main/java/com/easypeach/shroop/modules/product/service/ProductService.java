package com.easypeach.shroop.modules.product.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductGrade;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.domain.ProductStatus;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
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

	@Transactional
	public Product saveProduct(Long memberId, ProductRequest productRequest) {

		Member member = memberRepository.findById(memberId).get();
		Category category = categoryRepository.findById(productRequest.getCategoryId()).get();

		String title = productRequest.getTitle();
		ProductStatus productStatus = productRequest.getProductStatus();
		String brand = productRequest.getBrand();
		Long price = productRequest.getPrice();
		boolean isCheckedDeliveryFee = productRequest.isCheckedDeliveryFee();
		LocalDate purchaseDate = productRequest.getPurchaseDate();
		ProductGrade productGrade = productRequest.getProductGrade();
		boolean isDefect = productRequest.isDefect();
		String saleReason = productRequest.getSaleReason();
		String content = productRequest.getContent();
		Product product = Product.createProduct(member, title, productStatus, category, brand, price,
			isCheckedDeliveryFee, purchaseDate, productGrade, isDefect, saleReason, content);
		return productRepository.save(product);
	}

	@Transactional
	public Product findByProductId(Long productId) {
		Product product = productRepository.findById(productId).get();
		return product;
	}

	@Transactional
	public ProductImg getProductImg(Product product) {
		Product findProduct = productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException(""));
		findProduct.getProductImgList().get(0).getId();
		return findProduct.getProductImgList().get(0);
	}

}
