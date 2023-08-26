package com.easypeach.shroop.modules.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.service.AuthService;
import com.easypeach.shroop.modules.global.response.BasicResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping(value = "/sign-up")
	public ResponseEntity<BasicResponse> authenticate(@Validated @RequestBody final SignUpRequest signUpRequest) {
		authService.saveMember(signUpRequest);
		authService.sendPhoneAuth(signUpRequest.getLoginId());

		return ResponseEntity.ok(new BasicResponse("전달받은 인증 번호를 입력해주세요"));
	}

	@PostMapping(value = "/phone")
	public ResponseEntity<BasicResponse> signUp(@RequestBody Map<String, String> phoneMap) {
		String loginId = phoneMap.get("loginId");
		String phoneAuthNumber = phoneMap.get("phoneAuthNumber");
		authService.checkPhoneAuthNumber(loginId, phoneAuthNumber); //폰번호,인증번호 전달

		return ResponseEntity.ok(new BasicResponse("회원가입에 성공하였습니다"));
	}

}
