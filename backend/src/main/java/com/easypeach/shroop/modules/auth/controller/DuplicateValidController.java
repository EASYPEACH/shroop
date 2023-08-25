package com.easypeach.shroop.modules.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.dto.response.DuplicateValidResponse;
import com.easypeach.shroop.modules.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DuplicateValidController {

	private final MemberRepository memberRepository;

	@PostMapping(value = "/check/{type}")
	public ResponseEntity<DuplicateValidResponse> checkId(@RequestBody final Map<String, String> map,
		@PathVariable final String type) {
		String id = map.get(type);
		boolean result = getDuplicateResult(id, type);

		if (result == true) {
			return ResponseEntity.ok(new DuplicateValidResponse(false, "중복된 값입니다"));
		}
		return ResponseEntity.ok(new DuplicateValidResponse(true, "사용할 수 있는 값입니다."));
	}

	public boolean getDuplicateResult(final String name, final String type) {
		boolean result = false;
		if (type.equals("loginId")) {
			result = memberRepository.findByLoginId(name).isPresent();
		}
		if (type.equals("nickname")) {
			result = memberRepository.findByNickname(name).isPresent();
		}
		if (type.equals("phoneNumber")) {
			result = memberRepository.findByPhoneNumber(name).isPresent();
		}
		return result;
	}
}
