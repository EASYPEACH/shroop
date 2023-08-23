package com.easypeach.shroop.modules.auth.controller;

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
	public ResponseEntity<BasicResponse> signUp(@Validated @RequestBody final SignUpRequest signUpRequest) {
		authService.saveMember(signUpRequest);
		return ResponseEntity.ok(new BasicResponse("회원가입에 성공하였습니다."));
	}

}
