package com.easypeach.shroop.modules.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.infra.phone.NaverPhoneAuthService;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.notification.domain.Notification;
import com.easypeach.shroop.modules.notification.domain.NotificationRepository;
import com.easypeach.shroop.modules.notification.dto.response.NotificationResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;

	private final MemberService memberService;

	private final NaverPhoneAuthService phoneAuthService;

	@Transactional
	public void saveNotification(final Long memberId, final String title, final String link,
		final String message) {

		Member member = memberService.findById(memberId);

		Notification notification = Notification.createNotification(member, title, link, message, false);

		phoneAuthService.sendSms(member.getPhoneNumber(), "[슈룹] " + message); // 문자 발송

		notificationRepository.save(notification);
	}

	public List<Notification> findByMemberId(final Long memberId) {
		return notificationRepository.findByMemberIdOrderByCreateDateDesc(memberId);
	}

	@Transactional
	public String checkNotification(final Long notificationId) {
		Notification notification = findById(notificationId);
		notification.changeChecked(true);

		return notification.getLink();
	}

	public Notification findById(final Long notificationId) {
		return notificationRepository.findById(notificationId)
			.orElseThrow(() -> new RuntimeException("알림이 존재하지 않습니다."));
	}

	public List<NotificationResponse> toNotificationResponse(List<Notification> notificationList) {
		return notificationList.stream()
			.map((notification) -> {
				return new NotificationResponse(
					notification.getId(),
					notification.getMember().getId(),
					notification.getTitle(),
					notification.getLink(),
					notification.getMessage(),
					notification.isChecked(),
					notification.getCreateDate()
				);
			}).collect(Collectors.toList());
	}
}
