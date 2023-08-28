package com.easypeach.shroop.modules.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.dto.request.PhoneAuthRequest;
import com.easypeach.shroop.modules.auth.dto.request.PhoneNumber;
import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.dto.response.PhoneAuthUUID;
import com.easypeach.shroop.modules.auth.service.AuthService;
import com.easypeach.shroop.modules.auth.service.PhoneAuthService;
import com.easypeach.shroop.modules.global.response.BasicResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final PhoneAuthService phoneAuthService;

	@PostMapping(value = "/phone") // 폰인증 번호 보내기
	public ResponseEntity<PhoneAuthUUID> getAuthNumber(@RequestBody PhoneNumber phoneNumber) {
		Long UUID = phoneAuthService.sendAuthNumber(phoneNumber.getPhoneNumber());

		return ResponseEntity.ok(new PhoneAuthUUID(UUID)); // uuid 전달
	}

	@PostMapping(value = "check") // 포커스 아웃시 폰 인증 검사
	public ResponseEntity<BasicResponse> authenticate(@RequestBody PhoneAuthRequest phoneAuthRequest) {
		log.info("인증 요청 {}", phoneAuthRequest.getUuid());
		phoneAuthService.checkPhoneAuthNumber(phoneAuthRequest);

		return ResponseEntity.ok(new BasicResponse("인증에 성공하셨습니다."));
	}

	@PostMapping(value = "/sign-up") // 인증 완료 시, 폰 인증 검사 체크하고 회원 가입 처리
	public ResponseEntity<BasicResponse> signUp(@Validated @RequestBody final SignUpRequest signUpRequest) {
		PhoneAuthRequest phoneAuthRequest = new PhoneAuthRequest(signUpRequest.getUuid(),
			signUpRequest.getPhoneNumber(),
			signUpRequest.getPhoneAuthNumber());

		phoneAuthService.checkPhoneAuthNumber(phoneAuthRequest);
		authService.saveMember(signUpRequest);

		return ResponseEntity.ok(new BasicResponse("회원 가입이 완료되었습니다"));
	}

	@PostMapping("/test")
	public ResponseEntity<Void> test(@RequestBody Map<String, String> map) {
		String loginId = map.get("loginId");
		String password = map.get("password");
		String nickname = map.get("nickname");
		String phoneNumber = map.get("phoneNumber");
		authService.saveTestMember(loginId, password, nickname, phoneNumber);
		return ResponseEntity.ok().build();
	}

}
