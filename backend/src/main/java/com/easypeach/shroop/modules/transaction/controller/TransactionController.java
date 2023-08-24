package com.easypeach.shroop.modules.transaction.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.dto.request.TransactionCreateRequest;
import com.easypeach.shroop.modules.transaction.dto.request.TransactionInfoResponse;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionCreatedResponse;
import com.easypeach.shroop.modules.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buying")
public class TransactionController {
	private final ProductService productService;
	private final MemberService memberService;
	private final TransactionService transactionService;

	@GetMapping("/{productId}")
	public TransactionInfoResponse getBuyingForm(@PathVariable Long productId, @LoginMember Member member) {

		Product product = productService.findByProductId(productId);
		Member findedMember = memberService.findById(member.getId());

		return new TransactionInfoResponse(product.getTitle(),
			product.getPrice(), findedMember.getPoint());
	}

	@Transactional
	@PostMapping("/{productId}")
	public TransactionCreatedResponse buyingProduct(@RequestBody TransactionCreateRequest transactionCreateRequest,
		@PathVariable Long productId, @LoginMember Member member) {

		Product product = productService.findByProductId(productId);
		Member buyer = memberService.findById(member.getId());

		Transaction transaction = transactionService.saveTransaction(product, buyer, transactionCreateRequest);
		transactionService.subtractPoint(product, buyer);

		return new TransactionCreatedResponse(transaction.getId(),
			product.getTitle(),
			product.getPrice(), transactionCreateRequest.getBuyerName(), transactionCreateRequest.getBuyerLocation(),
			transactionCreateRequest.getBuyerPhoneNumber());
	}

}
