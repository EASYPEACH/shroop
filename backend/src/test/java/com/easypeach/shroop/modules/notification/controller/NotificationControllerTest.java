package com.easypeach.shroop.modules.notification.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.notification.domain.Notification;
import com.easypeach.shroop.modules.notification.dto.response.NotificationLinkResponse;
import com.easypeach.shroop.modules.notification.dto.response.NotificationResponse;
import com.easypeach.shroop.modules.notification.service.NotificationService;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest extends ControllerTest {

	@MockBean
	private NotificationService notificationService;

	@DisplayName("로그인 회원의 알림 조회")
	@Test
	void findAllByMemberIdTest() throws Exception {
		Long memberId = 1L;
		LocalDateTime now = LocalDateTime.now();
		List<Notification> notificationList = new ArrayList<>();
		List<NotificationResponse> notificationResponseList = new ArrayList<>();
		notificationResponseList.add(new NotificationResponse(1L, 1L, "알림 제목1", "maypage/0", "알림 내용1", true,
			now));
		notificationResponseList.add(new NotificationResponse(2L, 1L, "알림 제목2", "maypage/0", "알림 내용2", false,
			now));

		given(notificationService.findByMemberId(memberId)).willReturn(notificationList);
		given(notificationService.toNotificationResponse(notificationList)).willReturn(notificationResponseList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/notifications"))
			.andExpect(status().isOk())
			.andDo(document("notifications",
				responseFields(
					fieldWithPath("[].id").description("알림의 ID"),
					fieldWithPath("[].memberId").description("멤버 ID"),
					fieldWithPath("[].title").description("알림 제목"),
					fieldWithPath("[].link").description("알림 링크"),
					fieldWithPath("[].message").description("알림 내용"),
					fieldWithPath("[].checked").description("확인 여부"),
					fieldWithPath("[].createDate").description("생성일시")
				)))
			.andDo(print());

		verify(notificationService).findByMemberId(null);
		verify(notificationService).toNotificationResponse(notificationList);
	}

	@DisplayName("알림을 확인한다")
	@Test
	void changeChecked() throws Exception {
		Long notificationId = 1L;
		String link = "mypage/0";
		NotificationLinkResponse notificationLinkResponse = new NotificationLinkResponse(link);

		given(notificationService.checkNotification(notificationId)).willReturn(link);

		mockMvc.perform(patch("/api/notifications/{notificationId}", notificationId))
			.andExpect(status().isOk())
			.andDo(document("notificationChangeChecked",
				responseFields(
					fieldWithPath("link").description("알림의 링크")
				)))
			.andDo(print());

		verify(notificationService).checkNotification(notificationId);
	}
}