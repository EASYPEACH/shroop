package com.easypeach.shroop.modules.transaction.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.bank.service.BankService;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.notification.service.NotificationService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.respository.ProductRepository;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionRepository;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;
import com.easypeach.shroop.modules.transaction.dto.request.TransactionCreateRequest;
import com.easypeach.shroop.modules.transaction.dto.response.BuyerResponse;
import com.easypeach.shroop.modules.transaction.dto.response.HistoryResponse;
import com.easypeach.shroop.modules.transaction.dto.response.PageResponse;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionCreatedResponse;
import com.easypeach.shroop.modules.transaction.dto.response.TransactionInfoResponse;
import com.easypeach.shroop.modules.transaction.exception.IsNotBuyerException;
import com.easypeach.shroop.modules.transaction.exception.IsNotSellerException;
import com.easypeach.shroop.modules.transaction.exception.LackOfPointException;
import com.easypeach.shroop.modules.transaction.exception.SellerPurchaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TransactionService {
	private final TransactionRepository transactionRepository;

	private final ProductRepository productRepository;

	private final MemberService memberService;

	private final ProductService productService;

	private final NotificationService notificationService;

	private final BankService bankService;

	@Transactional
	public void createTransaction(final TransactionCreateRequest transactionCreateRequest, final Long productId,
		final Member member) {
		Product product = productService.findByProductId(productId);

		Long price = product.getPrice();
		Long fee = Math.round(price * 0.035);
		Long totalprice = price + fee;

		checkSeller(member, product.getSeller());

		// 거래 저장
		saveTransaction(productId, member.getId(), transactionCreateRequest);

		// 구매자에게 포인트 차감
		if (member.getPoint() >= totalprice) {
			subtractPoint(totalprice, member.getId());
		} else {
			throw new LackOfPointException("포인트가 부족합니다.");
		}

		//슈룹이 포인트 보관
		addPoint(productId, 1L);

		//슈룹이 수수료 가져감
		bankService.addMoney(fee, memberService.findById(1L));

		// phoneAuthService.sendSms(product.getSeller().getPhoneNumber(),
		// 	String.format("%s 판매자님 %s 상품이 결제되었습니다.", product.getSeller().getNickname(), product.getTitle()));

		log.info("{} 판매자님 {} 상품이 결제되었습니다.", product.getSeller().getNickname(), product.getTitle());
	}

	@Transactional
	public void saveTransaction(final Long productId, final Long buyerId,
		final TransactionCreateRequest transactionCreateRequest) {

		Product product = productService.findByProductId(productId);
		Member buyer = memberService.findById(buyerId);

		Transaction transaction = Transaction.createTransaction(buyer, product.getSeller(), product,
			TransactionStatus.PURCHASE_REQUEST, transactionCreateRequest.getBuyerName(),
			transactionCreateRequest.getBuyerLocation(),
			transactionCreateRequest.getBuyerPhoneNumber());
		transactionRepository.save(transaction);
	}

	@Transactional
	public void subtractPoint(final Long totalprice, final Long buyerId) {

		Member buyer = memberService.findById(buyerId);

		long updatedPoint = buyer.getPoint() - totalprice;
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
			throw new SellerPurchaseException("본인의 상품을 구매할 수 없습니다.");
		}
	}

	public TransactionInfoResponse createTransactionInfoResponse(final Long productId, final Member member) {
		Product product = productService.findByProductId(productId);
		ProductImg productImg = productService.getProductImg(product);
		String productImgUrl = productImg.getProductImgUrl();
		Member findedMember = memberService.findById(member.getId());

		return new TransactionInfoResponse(productImgUrl, product.getTitle(),
			product.getPrice(), findedMember.getPoint());
	}

	public TransactionCreatedResponse createTransactionCreatedResponse(final Long productId) {
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

	public List<HistoryResponse> findAllBuyingHistory(final Member member) {
		List<Transaction> transactionList = transactionRepository.findAllByBuyer(member);
		return transactionList.stream()
			.map(HistoryResponse::new)
			.collect(Collectors.toList());
	}

	public PageResponse findAllSellingHistory(final Member member, final Pageable pageable) {

		Page<Product> page = productRepository.findBySeller(member, pageable);
		int pageCount = page.getTotalPages();
		List<HistoryResponse> historyResponseList = page
			.map(HistoryResponse::new)
			.getContent();
		return PageResponse.createPageResponse(pageCount, historyResponseList);
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
		Long sellerId = transaction.getSeller().getId();
		Long buyerId = transaction.getBuyer().getId();

		if (buyerId != memberId) {
			throw IsNotBuyerException.isNotBuyerException();
		}

		transaction.updateStatus(TransactionStatus.PURCHASE_CONFIRM);

		addPoint(productId, sellerId);

		String title = "구매 확정";
		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 구매가 확정되었습니다.";

		// 판매자 알림
		notificationService.saveNotification(sellerId, title, "/mypage/2", message);

		// 구매자 알림
		notificationService.saveNotification(buyerId, title, "/mypage/1", message);
	}

	@Transactional
	public void returnConfirm(final Long memberId, final Long productId) {

		Transaction transaction = findByProductId(productId);
		Long sellerId = transaction.getSeller().getId();
		Long buyerId = transaction.getBuyer().getId();

		if (sellerId != memberId) {
			throw IsNotSellerException.isNotSellerException();
		}

		transaction.updateStatus(TransactionStatus.RETURN_COMPLETE);

		String title = "반품 확정";
		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 반품이 확정되었습니다.";

		// 판매자 알림
		notificationService.saveNotification(sellerId, title, "/mypage/2", message);

		// 구매자 알림
		notificationService.saveNotification(buyerId, title, "/mypage/1", message);

		addPoint(productId, buyerId);
	}

}
