package com.easypeach.shroop.modules.notification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.notification.domain.Notification;
import com.easypeach.shroop.modules.notification.dto.request.NotificationRequest;
import com.easypeach.shroop.modules.notification.dto.response.NotificationLinkResponse;
import com.easypeach.shroop.modules.notification.dto.response.NotificationResponse;
import com.easypeach.shroop.modules.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/notifications")
@RestController
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping
	public ResponseEntity<List<NotificationResponse>> findAllByMemberId(@LoginMember final Member member) {
		List<Notification> notificationList = notificationService.findByMemberId(member.getId());
		List<NotificationResponse> responses = notificationService.toNotificationResponse(notificationList);
		return ResponseEntity.status(HttpStatus.OK).body(responses);
	}

	@PatchMapping("/{notificationId}")
	public ResponseEntity<NotificationLinkResponse> changeChecked(@PathVariable final Long notificationId) {
		String link = notificationService.checkNotification(notificationId);
		NotificationLinkResponse response = new NotificationLinkResponse(link);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// 테스트용 API: 알림 생성
	@PostMapping
	public ResponseEntity<Void> saveNotification(@LoginMember Member member,
		@RequestBody NotificationRequest notificationRequest) {
		String title = notificationRequest.getTitle();
		String link = notificationRequest.getLink();
		String message = notificationRequest.getMessage();

		notificationService.saveNotification(member.getId(), title, link, message);

		return ResponseEntity.status(HttpStatus.OK).build();

	}

}
