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
import com.easypeach.shroop.modules.transaction.dto.response.BuyerResponse;
import com.easypeach.shroop.modules.transaction.exception.SellerPurchaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final MemberService memberService;
	private final ProductService productService;

	@Transactional
	public void saveTransaction(final Long productId, final Long buyerId,
		final TransactionCreateRequest transactionCreateRequest) {

		Product product = productService.findByProductId(productId);
		Member buyer = memberService.findById(buyerId);

		Transaction transaction = Transaction.createTransaction(buyer, product.getSeller(), product,
			TransactionStatus.TRANSACTION_PROGRESS, transactionCreateRequest.getBuyerName(),
			transactionCreateRequest.getBuyerLocation(),
			transactionCreateRequest.getBuyerPhoneNumber());
		transactionRepository.save(transaction);
	}

	@Transactional
	public void subtractPoint(final Long productId, final Long buyerId) {

		Product product = productService.findByProductId(productId);
		Member buyer = memberService.findById(buyerId);

		long updatedPoint = buyer.getPoint() - product.getPrice();
		buyer.updatePoint(updatedPoint);
	}

	public void checkSeller(final Member member, final Member seller) {
		if (seller.getId() == member.getId()) {
			throw SellerPurchaseException.SellerBuyingMyProduct();
		}
	}

	public BuyerResponse getBuyer(final Long productId) {
		Transaction transaction = findByProductId(productId);
		return new BuyerResponse(
			transaction.getBuyerName(),
			transaction.getBuyerLocation(),
			transaction.getBuyerPhoneNumber()
		);
	}

	public Transaction findByProductId(final Long productId) {
		return transactionRepository.getByProductId(productId);
	}

}
