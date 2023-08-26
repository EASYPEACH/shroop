package com.easypeach.shroop.modules.notification.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NotificationRequest {

	private String title;

	private String link;

	private String message;

	private LocalDateTime createDate;

	public NotificationRequest(String title, String link, String message, LocalDateTime createDate) {
		this.title = title;
		this.link = link;
		this.message = message;
		this.createDate = createDate;
	}
}
