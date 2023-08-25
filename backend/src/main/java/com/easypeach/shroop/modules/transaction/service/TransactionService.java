package com.easypeach.shroop.modules.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final ProductService productService;
	private final MemberService memberService;

	@Transactional
	public void saveTransaction(final Product product, final Member buyer,
		final TransactionCreateRequest transactionCreateRequest) {
		Transaction transaction = Transaction.createTransaction(buyer, product.getSeller(), product,
			TransactionStatus.TRANSACTION_PROGRESS, transactionCreateRequest.getBuyerName(),
			transactionCreateRequest.getBuyerLocation(),
			transactionCreateRequest.getBuyerPhoneNumber());
		transactionRepository.save(transaction);
	}

	@Transactional
	public void subtractPoint(final Product product, final Member buyer) {
		long updatedPoint = buyer.getPoint() - product.getPrice();
		buyer.updateMember(updatedPoint);
	}

	public Transaction findById(final Long productId) {
		return transactionRepository.findById(productId).get();
	}
}
