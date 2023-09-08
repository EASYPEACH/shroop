package com.easypeach.shroop.modules.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointRequest {
	private Long point;

	public PointRequest(Long point) {
		this.point = point;
	}

	public static PointRequest createPointRequest(Long point) {
		PointRequest pointRequest = new PointRequest();
		pointRequest.point = point;
		return pointRequest;
	}
}
