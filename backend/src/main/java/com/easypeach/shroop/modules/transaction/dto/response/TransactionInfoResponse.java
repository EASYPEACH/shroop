package com.easypeach.shroop.modules.transaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInfoResponse {
	private String title;
	private Long price;
	private Long point;

}
