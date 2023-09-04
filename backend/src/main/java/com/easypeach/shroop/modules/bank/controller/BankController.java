package com.easypeach.shroop.modules.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.bank.dto.LinkBankRequest;
import com.easypeach.shroop.modules.bank.service.BankService;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/bank")
@RestController
@RequiredArgsConstructor
public class BankController {

	private final BankService bankService;

	@PostMapping("/linking")
	public ResponseEntity<BasicResponse> linkAccount(@Validated @RequestBody final LinkBankRequest linkBankRequest,
		@LoginMember final Member member) {
		bankService.linkingAccount(linkBankRequest, member);
		return ResponseEntity.ok(new BasicResponse("계좌가 연동되었습니다."));
	}
}
