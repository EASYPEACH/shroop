package com.easypeach.shroop.modules.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.member.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {

	@PostMapping(value = "/test")
	public String test(@LoginMember Member member) {

		return "ok";
	}

	@GetMapping(value = "/jenkins")
	public String jenkins() {
		return "jenkins test ok2";
	}

}
