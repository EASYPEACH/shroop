package com.easypeach.shroop.modules.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.notification.service.NotificationService;
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
import com.easypeach.shroop.modules.transaction.exception.IsNotBuyerException;
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

	private final NotificationService notificationService;

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

		long updatedPoint = Math.max(buyer.getPoint() - product.getPrice(), 0L);
		buyer.updatePoint(updatedPoint);
	}

	@Transactional
	public void addPoint(final Long productId, final Long sallerId) {

		Product product = productService.findByProductId(productId);
		Member saller = memberService.findById(sallerId);

		long updatedPoint = saller.getPoint() + product.getPrice();
		saller.updatePoint(updatedPoint);
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

	@Transactional
	public void cancelTransaction(final Long memberId, final Long productId) {
		Transaction transaction = findByProductId(productId);
		transactionRepository.delete(transaction);

		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 구매가 취소 되었습니다.";
		log.info("메시지 : " + message);

		// 판매자 알림
		notificationService.saveNotification(transaction.getSeller().getId(), "구매 취소", "/mypage/2", message);

		// 구매자 알림
		notificationService.saveNotification(memberId, "구매 취소", "/mypage/1", message);

	}

	@Transactional
	public void purchaseConfirm(final Long memberId, final Long productId) {

		Transaction transaction = findByProductId(productId);

		if (transaction.getBuyer().getId() != memberId) {
			throw IsNotBuyerException.isNotBuyerException();
		}

		transaction.updateStatus(TransactionStatus.TRANSACTION_COMPLETE);

		addPoint(productId, memberId);
	}

}
