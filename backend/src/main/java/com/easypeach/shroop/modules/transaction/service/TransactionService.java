package com.easypeach.shroop.modules.transaction.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.bank.exception.NoSuchBankException;
import com.easypeach.shroop.modules.bank.service.BankService;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.ShroopMember;
import com.easypeach.shroop.modules.member.dto.request.PointRequest;
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
		Member foundMember = memberService.findById(member.getId());
		if (foundMember.getAccount() == null) {
			throw NoSuchBankException.bankNotExistException();
		}
		Product product = productService.findByProductId(productId);

		Long price = product.getPrice();
		Long fee = Math.round(price * 0.035);
		Long totalPrice = price + fee;

		checkSeller(foundMember, product.getSeller());

		// 거래 저장
		saveTransaction(productId, foundMember.getId(), transactionCreateRequest);

		// 구매자에게 포인트 차감
		if (foundMember.getPoint() >= totalPrice) {
			subtractPoint(totalPrice, foundMember.getId());
		} else {
			throw new LackOfPointException("포인트가 부족합니다.");
		}

		//슈룹이 포인트 보관
		addPoint(productId, ShroopMember.SHROOP_ID.getId());

		//슈룹이 수수료 가져감
		bankService.addMoney(PointRequest.createPointRequest(fee),
			memberService.findById(ShroopMember.SHROOP_ID.getId()));

		String message = product.getTitle() + " 상품이 판매 되었습니다.";
		notificationService.saveNotification(product.getSeller().getId(), "결제완료", "/mypage/sellList", message);

	}

	@Transactional
	public void saveTransaction(final Long productId, final Long buyerId,
		final TransactionCreateRequest transactionCreateRequest) {

		Product product = productService.findByProductId(productId);
		Member buyer = memberService.findById(buyerId);

		Transaction transaction = Transaction.createTransaction(buyer, product.getSeller(), product,
			TransactionStatus.PURCHASE_REQUEST, transactionCreateRequest.getBuyerName(),
			transactionCreateRequest.getBuyerPhoneNumber(),
			transactionCreateRequest.getBuyerPostcode(),
			transactionCreateRequest.getBuyerLocation(),
			transactionCreateRequest.getBuyerDetailLocation()
		);
		transactionRepository.save(transaction);
	}

	@Transactional
	public void subtractPoint(final Long totalPrice, final Long buyerId) {

		Member buyer = memberService.findById(buyerId);

		long updatedPoint = buyer.getPoint() - totalPrice;
		buyer.updatePoint(updatedPoint);
	}

	@Transactional
	public void addPoint(final Long productId, final Long sellerId) {

		Product product = productService.findByProductId(productId);
		Member seller = memberService.findById(sellerId);

		long updatedPoint = seller.getPoint() + product.getPrice();
		seller.updatePoint(updatedPoint);
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

		return new TransactionCreatedResponse(transaction.getId(), product.getId(), productImgUrl, product.getTitle(),
			product.getPrice(),
			transaction.getBuyerName(), transaction.getBuyerPhoneNumber(), transaction.getBuyerPostcode(),
			transaction.getBuyerLocation(), transaction.getBuyerDetailLocation());
	}

	public BuyerResponse getBuyer(final Long productId) {
		Transaction transaction = findByProductId(productId);
		return new BuyerResponse(
			transaction.getBuyerName(),
			transaction.getBuyerPhoneNumber(),
			transaction.getBuyerPostcode(),
			transaction.getBuyerLocation(),
			transaction.getBuyerDetailLocation()
		);
	}

	public Transaction findByProductId(final Long productId) {
		return transactionRepository.getByProductId(productId);
	}

	public PageResponse findAllBuyingHistory(final Member member, final Pageable pageable) {
		Member foundMember = memberService.findById(member.getId());
		Page<Transaction> page = transactionRepository.findByBuyer(foundMember, pageable);
		int pageCount = page.getTotalPages();
		List<HistoryResponse> historyResponseList = page
			.map(p -> productService.findByProductId(p.getProduct().getId()))
			.map(HistoryResponse::new)
			.getContent();
		return PageResponse.createPageResponse(pageCount, historyResponseList);
	}

	public PageResponse findAllSellingHistory(final Member member, final Pageable pageable) {
		Member foundMember = memberService.findById(member.getId());
		Page<Product> page = productRepository.findBySeller(foundMember, pageable);
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

		Long price = transaction.getProduct().getPrice();
		Long commission = Math.round(price * 0.035);
		Member shroopAdmin = memberService.findById(ShroopMember.SHROOP_ID.getId());

		// 구매자 포인트 환불
		transaction.getBuyer().addPoint(price + commission);
		shroopAdmin.subtractPoint(price);
		bankService.subtractMoney(new PointRequest(commission), shroopAdmin);

		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 구매가 취소 되었습니다.";
		log.info("메시지 : " + message);

		// 판매자 알림
		notificationService.saveNotification(transaction.getSeller().getId(), "구매 취소", "/mypage/sellList", message);

		// 구매자 알림
		notificationService.saveNotification(memberId, "구매 취소", "/mypage/purchaseList", message);

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

		subtractPoint(transaction.getProduct().getPrice(), ShroopMember.SHROOP_ID.getId());

		addPoint(productId, sellerId);

		String title = "구매 확정";
		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 구매가 확정되었습니다.";

		// 판매자 회원 등급 상승
		memberService.findById(sellerId).addGradeScore(10L);

		// 판매자 알림
		notificationService.saveNotification(sellerId, title, "/mypage/sellList", message);

		// 구매자 알림
		notificationService.saveNotification(buyerId, title, "/mypage/purchaseList", message);
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

		subtractPoint(transaction.getProduct().getPrice(), ShroopMember.SHROOP_ID.getId());

		addPoint(productId, buyerId);

		String title = "반품 확정";
		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 반품이 확정되었습니다.";

		// 판매자 알림
		notificationService.saveNotification(sellerId, title, "/mypage/sellList", message);

		// 구매자 알림
		notificationService.saveNotification(buyerId, title, "/mypage/purchaseList", message);

	}

	@Transactional
	public void resale(final Long productId) {

		Transaction transaction = findByProductId(productId);
		transactionRepository.delete(transaction);

		Long sellerId = transaction.getSeller().getId();
		String title = "상품 재판매";
		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 상품이 판매로 전환되었습니다.";

		// 판매자 알림
		notificationService.saveNotification(sellerId, title, "/mypage/sellList", message);

	}

}
