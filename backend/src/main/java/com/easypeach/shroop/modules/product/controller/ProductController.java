package com.easypeach.shroop.modules.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.product.dto.response.ProductCreatedResponse;
import com.easypeach.shroop.modules.product.dto.response.ProductResponse;
import com.easypeach.shroop.modules.product.service.ProductImgService;
import com.easypeach.shroop.modules.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	private final ProductImgService productImgService;

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProduct() {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.getProductInfo(productId));
	}

	@PostMapping
	public ResponseEntity<ProductCreatedResponse> saveProduct(@LoginMember Member member,
		@RequestPart(value = "productImgList") List<MultipartFile> productImgList,
		@RequestPart(value = "defectImgList", required = false) List<MultipartFile> defectImgList,
		@Validated @RequestPart ProductRequest productRequest) {
		Product product = productService.saveProduct(member.getId(), productRequest);
		productImgService.saveProductImg(productImgList, defectImgList, product, productRequest.getIsDefect());
		return ResponseEntity.status(HttpStatus.OK).body(new ProductCreatedResponse(product.getId()));
	}

	@PatchMapping
	public ResponseEntity<ProductCreatedResponse> updateProduct(@LoginMember Member member,
		@RequestPart(value = "productId") Long productId,
		@RequestPart(value = "productImgList") List<MultipartFile> productImgList,
		@RequestPart(value = "defectImgList", required = false) List<MultipartFile> defectImgList,
		@RequestPart ProductRequest productRequest) {
		Product product = productService.updateProduct(member.getId(), productId, productRequest);
		productImgService.updateProductImgList(productImgList, defectImgList, productId,
			productRequest.getIsDefect());
		return ResponseEntity.status(HttpStatus.OK).body(new ProductCreatedResponse(product.getId()));
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<BasicResponse> deleteProduct(@LoginMember Member member, @PathVariable Long productId) {
		productService.deleteProduct(member.getId(), productId);
		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("삭제가 완료되었습니다"));
	}
}
