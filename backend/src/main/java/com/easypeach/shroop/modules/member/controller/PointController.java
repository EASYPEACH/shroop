package com.easypeach.shroop.modules.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.bank.service.BankService;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/point")
@RestController
@RequiredArgsConstructor
public class PointController {
	private final MemberService memberService;
	private final BankService bankService;

	@PatchMapping(value = "/charging")
	public ResponseEntity<Long> chargePoint(@RequestBody final Long point, @LoginMember final Member member) {
		bankService.subtractMoney(point, member);
		Long updatedPoint = memberService.plusPoint(point, member);
		return ResponseEntity.status(HttpStatus.OK).body(updatedPoint);
	}

	@PatchMapping(value = "/exchanging")
	public ResponseEntity<Long> exchangePoint(@RequestBody final Long point, @LoginMember final Member member) {
		Long updatedPoint = memberService.subtractPoint(point, member);
		bankService.addPoint(point, member);

		return ResponseEntity.status(HttpStatus.OK).body(updatedPoint);
	}

}
