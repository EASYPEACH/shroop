package com.easypeach.shroop.modules.notification.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NotificationLinkResponse {

	private String link;

	public NotificationLinkResponse(final String link) {
		this.link = link;
	}
}
