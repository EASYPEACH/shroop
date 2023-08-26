package com.easypeach.shroop.modules.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.dto.request.TransactionCreateRequest;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionCreatedResponse;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionInfoResponse;
import com.easypeach.shroop.modules.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buying")
public class TransactionController {
	private final ProductService productService;
	private final MemberService memberService;
	private final TransactionService transactionService;

	@GetMapping("/{productId}")
	public ResponseEntity<TransactionInfoResponse> getBuyingForm(final @PathVariable Long productId,
		final @LoginMember Member member) {
		Product product = productService.findByProductId(productId);
		ProductImg productImg = productService.getProductImg(product);
		String productImgUrl = productImg.getProductImgUrl();
		Member findedMember = memberService.findById(member.getId());

		return ResponseEntity.status(HttpStatus.OK).body(new TransactionInfoResponse(productImgUrl, product.getTitle(),
			product.getPrice(), findedMember.getPoint()));
	}

	@PostMapping("/{productId}")
	public ResponseEntity<BasicResponse> buyingProduct(
		final @RequestBody TransactionCreateRequest transactionCreateRequest,
		final @PathVariable Long productId, final @LoginMember Member member) {

		Product product = productService.findByProductId(productId);
		Member buyer = memberService.findById(member.getId());

		transactionService.saveTransaction(product, buyer, transactionCreateRequest);
		transactionService.subtractPoint(product, buyer);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("결제가 완료되었습니다."));

	}

	@GetMapping("/completed/{productId}")
	public ResponseEntity<TransactionCreatedResponse> getBuyingCompletedForm(final @PathVariable Long productId) {

		Product product = productService.findByProductId(productId);
		Transaction transaction = product.getTransaction();
		ProductImg productImg = productService.getProductImg(product);
		String productImgUrl = productImg.getProductImgUrl();

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				new TransactionCreatedResponse(transaction.getId(), productImgUrl, product.getTitle(),
					product.getPrice(),
					transaction.getBuyerName(), transaction.getBuyerLocation(), transaction.getBuyerPhoneNumber()));
	}

}

