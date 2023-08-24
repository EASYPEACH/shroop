package com.easypeach.shroop.modules.transaction.service;

import org.springframework.stereotype.Service;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionRepository;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;
import com.easypeach.shroop.modules.transaction.dto.request.TransactionCreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final ProductService productService;
	private final MemberService memberService;

	public Transaction saveTransaction(Product product, Member buyer,
		TransactionCreateRequest transactionCreateRequest) {
		Transaction transaction = Transaction.createTransaction(buyer, product.getSeller(), product,
			TransactionStatus.TRANSACTION_PROGRESS, transactionCreateRequest.getBuyerName(),
			transactionCreateRequest.getBuyerLocation(),
			transactionCreateRequest.getBuyerPhoneNumber());
		return transactionRepository.save(transaction);
	}

	public void subtractPoint(Product product, Member buyer) {
		long updatedPoint = buyer.getPoint() - product.getPrice();
		buyer.updateMember(updatedPoint);
	}
}
