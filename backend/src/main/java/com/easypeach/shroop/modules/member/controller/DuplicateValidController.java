package com.easypeach.shroop.modules.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.member.domain.DuplicateCheckType;
import com.easypeach.shroop.modules.auth.dto.response.DuplicateValidResponse;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DuplicateValidController {

	private final MemberRepository memberRepository;
	private final MemberService memberService;

	@PostMapping(value = "/check/{type}")
	public ResponseEntity<DuplicateValidResponse> checkId(@RequestBody final Map<String, String> map,
		@PathVariable final String type) {
		String value = map.get(type);
		DuplicateCheckType checkType = DuplicateCheckType.createCheckType(type);
		boolean result = memberService.getDuplicateResult(value, checkType);

		if (result == true) {
			return ResponseEntity.ok(new DuplicateValidResponse(false, "중복된 값입니다"));
		}
		return ResponseEntity.ok(new DuplicateValidResponse(true, "사용할 수 있는 값입니다."));
	}

}
