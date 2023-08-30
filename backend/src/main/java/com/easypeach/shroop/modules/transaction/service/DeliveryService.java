package com.easypeach.shroop.modules.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.notification.service.NotificationService;
import com.easypeach.shroop.modules.transaction.domain.Delivery;
import com.easypeach.shroop.modules.transaction.domain.DeliveryRepository;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;
import com.easypeach.shroop.modules.transaction.dto.request.DeliveryRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;

	private final TransactionService transactionService;

	private final NotificationService notificationService;

	@Transactional
	public void saveDelivery(final Long memberId, final Long productId, final DeliveryRequest deliveryRequest) {
		String trackingNumber = deliveryRequest.getTrackingNumber();
		String parcel = deliveryRequest.getParcel();

		Delivery delivery = Delivery.createDelivery(trackingNumber, parcel);
		Delivery newDelivery = deliveryRepository.save(delivery);

		Transaction transaction = transactionService.findByProductId(productId);
		transaction.updateDelivery(newDelivery);
		transaction.updateStatus(TransactionStatus.COMPLETE);

		Long sellerId = transaction.getSeller().getId();
		Long buyer = transaction.getBuyer().getId();
		String productTitle = transaction.getProduct().getTitle().length() > 10 ?
			transaction.getProduct().getTitle().substring(0, 10) + "..." : transaction.getProduct().getTitle();
		String message = "'" + productTitle + "'의 택배 등록이 완료되었습니다.";
		log.info("메시지 : " + message);

		// 판매자 알림
		notificationService.saveNotification(sellerId, "택배 등록", "/mypage/2", message);

		// 구매자 알림
		notificationService.saveNotification(buyer, "택배 등록", "/mypage/1", message);

	}
}

