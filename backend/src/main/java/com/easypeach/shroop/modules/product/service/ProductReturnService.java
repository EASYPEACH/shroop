package com.easypeach.shroop.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.notification.service.NotificationService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductReturn;
import com.easypeach.shroop.modules.product.dto.request.ProductReturnRequest;
import com.easypeach.shroop.modules.product.dto.response.ProductReturnImgResponse;
import com.easypeach.shroop.modules.product.dto.response.ProductReturnResponse;
import com.easypeach.shroop.modules.product.respository.ProductReturnImgRepository;
import com.easypeach.shroop.modules.product.respository.ProductReturnRepository;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;
import com.easypeach.shroop.modules.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductReturnService {

	private final ProductReturnRepository productReturnRepository;

	private final ProductService productService;

	private final ProductReturnImgService productReturnImgService;

	private final TransactionService transactionService;

	private final NotificationService notificationService;

	private final ProductReturnImgRepository productReturnImgRepository;

	@Transactional
	public void saveProductReturn(final Long memberId, final Long productId,
		final ProductReturnRequest productReturnRequest,
		final List<MultipartFile> productReturnImgList) {
		Product product = productService.findByProductId(productId);
		ProductReturn productReturn = ProductReturn.createProductReturn(product, productReturnRequest.getContent());
		ProductReturn newProductReturn = productReturnRepository.save(productReturn);

		Transaction transaction = transactionService.findByProductId(productId);
		transaction.updateStatus(TransactionStatus.RETURN_REQUEST);

		productReturnImgService.saveProductReturnImg(newProductReturn, productReturnImgList);

		Long sellerId = transaction.getSeller().getId();
		Long buyerId = transaction.getBuyer().getId();
		String title = "반품 신청";
		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 반품이 신청되었습니다.";

		// 판매자 알림
		notificationService.saveNotification(sellerId, title, "/mypage/sellList", message);

		// 구매자 알림
		notificationService.saveNotification(buyerId, title, "/mypage/purchaseList", message);

	}

	public ProductReturnResponse findById(final Long productId) {
		ProductReturnResponse productReturnResponse = productReturnRepository.getByProductId(productId);
		List<ProductReturnImgResponse> productReturnImgResponseList = productReturnImgRepository.findByProductReturnId(
			productReturnResponse.getId());

		productReturnResponse.updateProductReturnImgResponseList(productReturnImgResponseList);

		return productReturnResponse;

	}
}
