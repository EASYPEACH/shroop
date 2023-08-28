package com.easypeach.shroop.modules.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionRepository;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;
import com.easypeach.shroop.modules.transaction.dto.request.TransactionCreateRequest;
import com.easypeach.shroop.modules.transaction.dto.response.BuyerResponse;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionCreatedResponse;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionInfoResponse;
import com.easypeach.shroop.modules.transaction.exception.SellerPurchaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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

	public TransactionInfoResponse createTransactionInfoResponse(Long productId, Member member) {
		Product product = productService.findByProductId(productId);
		ProductImg productImg = productService.getProductImg(product);
		String productImgUrl = productImg.getProductImgUrl();
		Member findedMember = memberService.findById(member.getId());

		return new TransactionInfoResponse(productImgUrl, product.getTitle(),
			product.getPrice(), findedMember.getPoint());
	}

	@Transactional
	public void createTransaction(TransactionCreateRequest transactionCreateRequest, Long productId, Member member) {
		Product product = productService.findByProductId(productId);
		checkSeller(member, product.getSeller());

		saveTransaction(productId, member.getId(), transactionCreateRequest);
		subtractPoint(productId, member.getId());

		// phoneAuthService.sendSms(product.getSeller().getPhoneNumber(),
		// 	String.format("%s 판매자님 %s 상품이 결제되었습니다.", product.getSeller().getNickname(), product.getTitle()));

		log.info("{} 판매자님 {} 상품이 결제되었습니다.", product.getSeller().getNickname(), product.getTitle());
	}

	public TransactionCreatedResponse createTransactionCreatedResponse(Long productId) {
		Product product = productService.findByProductId(productId);
		Transaction transaction = product.getTransaction();
		ProductImg productImg = productService.getProductImg(product);
		String productImgUrl = productImg.getProductImgUrl();

		return new TransactionCreatedResponse(transaction.getId(), productImgUrl, product.getTitle(),
			product.getPrice(),
			transaction.getBuyerName(), transaction.getBuyerLocation(), transaction.getBuyerPhoneNumber());
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
