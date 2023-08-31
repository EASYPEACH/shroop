package com.easypeach.shroop.modules.transaction.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageResponse {

	private int pageCount;

	private List<HistoryResponse> historyResponseList;

	public static PageResponse createPageResponse(int pageCount, List<HistoryResponse> historyResponseList) {
		PageResponse pageResponse = new PageResponse();
		pageResponse.pageCount = pageCount;
		pageResponse.historyResponseList = historyResponseList;
		return pageResponse;
	}
}
