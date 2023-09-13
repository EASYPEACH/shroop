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
import com.easypeach.shroop.modules.member.dto.reponse.PointResponse;
import com.easypeach.shroop.modules.member.dto.request.PointRequest;
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
	public ResponseEntity<PointResponse> chargePoint(@RequestBody final PointRequest pointRequest,
		@LoginMember final Member member) {

		PointResponse pointResponse = memberService.chargePoint(pointRequest, member);
		return ResponseEntity.status(HttpStatus.OK).body(pointResponse);
	}

	@PatchMapping(value = "/exchanging")
	public ResponseEntity<PointResponse> exchangePoint(@RequestBody final PointRequest pointRequest,
		@LoginMember final Member member) {

		PointResponse pointResponse = memberService.exchangePoint(pointRequest, member);

		return ResponseEntity.status(HttpStatus.OK).body(pointResponse);
	}

}
