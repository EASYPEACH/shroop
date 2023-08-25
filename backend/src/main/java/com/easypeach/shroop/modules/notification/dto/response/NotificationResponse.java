package com.easypeach.shroop.modules.notification.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NotificationResponse {

	private Long id;

	private Long memberId;

	private String title;

	private String link;

	private String message;

	private boolean checked;

	private LocalDateTime createDate;

	public NotificationResponse(Long id, Long memberId, String title, String link, String message, boolean checked,
		LocalDateTime createDate) {
		this.id = id;
		this.memberId = memberId;
		this.title = title;
		this.link = link;
		this.message = message;
		this.checked = checked;
		this.createDate = createDate;
	}

}
