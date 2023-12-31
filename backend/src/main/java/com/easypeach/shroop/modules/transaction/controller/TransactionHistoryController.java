package com.easypeach.shroop.modules.transaction.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.transaction.dto.response.PageResponse;
import com.easypeach.shroop.modules.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class TransactionHistoryController {

	private final TransactionService transactionService;

	@GetMapping("/buying")
	public ResponseEntity<PageResponse> getBuyingHistory(@LoginMember Member member, Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllBuyingHistory(member, pageable));
	}

	@GetMapping("/selling")
	public ResponseEntity<PageResponse> getSellingHistory(@LoginMember Member member, Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(transactionService.findAllSellingHistory(member, pageable));
	}
}
