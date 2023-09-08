package com.easypeach.shroop.modules.member.dto.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointResponse {
	private Long point;

	public static PointResponse createPointResponse(Long point) {
		PointResponse pointResponse = new PointResponse();
		pointResponse.point = point;
		return pointResponse;
	}
}
