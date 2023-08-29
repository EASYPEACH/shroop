package com.easypeach.shroop.modules.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.dto.request.ProductReturnRequest;
import com.easypeach.shroop.modules.product.service.ProductReturnService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/return")
@RestController
@RequiredArgsConstructor
public class ProductReturnController {

	private final ProductReturnService productReturnService;

	@PostMapping("/{productId}")
	public ResponseEntity<BasicResponse> saveProductReturn(
		@LoginMember final Member member,
		@PathVariable final Long productId,
		@Validated @RequestPart final ProductReturnRequest productReturnRequest,
		@RequestPart(value = "productReturnImgList") List<MultipartFile> productReturnImgList
	) {
		productReturnService.saveProductReturn(member.getId(), productId, productReturnRequest, productReturnImgList);
		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("반품 요청이 완료되었습니다."));
	}

}
