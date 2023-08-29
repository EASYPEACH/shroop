package com.easypeach.shroop.modules.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.dto.request.TransactionCreateRequest;
import com.easypeach.shroop.modules.transaction.dto.response.BuyerResponse;
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
	// private final PhoneAuthService phoneAuthService;

	@GetMapping("/{productId}")
	public ResponseEntity<TransactionInfoResponse> getBuyingForm(@PathVariable Long productId,
		@LoginMember Member member) {
		return ResponseEntity.status(HttpStatus.OK).body(transactionService.createTransactionInfoResponse(productId,
			member));
	}

	@PostMapping("/{productId}")
	public ResponseEntity<BasicResponse> buyingProduct(
		@Validated @RequestBody TransactionCreateRequest transactionCreateRequest,
		@PathVariable Long productId, final @LoginMember Member member) {

		transactionService.createTransaction(transactionCreateRequest, productId, member);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("결제가 완료되었습니다."));

	}

	@GetMapping("/completed/{productId}")
	public ResponseEntity<TransactionCreatedResponse> getBuyingCompletedForm(final @PathVariable Long productId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(transactionService.createTransactionCreatedResponse(productId));
	}

	@GetMapping("/buyer/{productId}")
	public ResponseEntity<BuyerResponse> getBuyer(final @PathVariable Long productId) {
		BuyerResponse buyerResponse = transactionService.getBuyer(productId);
		return ResponseEntity.status(HttpStatus.OK).body(buyerResponse);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<BasicResponse> cancelTransaction(final @LoginMember Member member,
		final @PathVariable Long productId) {
		transactionService.cancelTransaction(member.getId(), productId);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("구매 취소가 완료되었습니다."));
	}

	@PatchMapping("/confirm/{productId}")
	public ResponseEntity<BasicResponse> purchaseConfirm(final @LoginMember Member member,
		final @PathVariable Long productId) {
		transactionService.purchaseConfirm(member.getId(), productId);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("구매 확정이 완료되었습니다."));
	}

}

