package com.easypeach.shroop.modules.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.infra.aop.log.user.UserTrace;
import com.easypeach.shroop.modules.auth.dto.response.DuplicateValidResponse;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.transaction.dto.request.DeliveryRequest;
import com.easypeach.shroop.modules.transaction.service.DeliveryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

	private final DeliveryService deliveryService;

	@UserTrace(value = "운송장 번호를 등록 하였습니다")
	@PostMapping("/{productId}")
	public ResponseEntity<BasicResponse> saveDelivery(
		final @PathVariable Long productId,
		final @RequestBody DeliveryRequest deliveryRequest) {

		deliveryService.saveDelivery(productId, deliveryRequest);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("택배 등록이 완료되었습니다."));
	}

	@GetMapping("/duplicate")
	public ResponseEntity<DuplicateValidResponse> duplicateCheckTrackingNumber(
		final @RequestParam String trackingNumber) {
		boolean response = deliveryService.duplicateCheckTrackingNumber(trackingNumber);
		return ResponseEntity.status(HttpStatus.OK).body(new DuplicateValidResponse(response, ""));
	}

}
