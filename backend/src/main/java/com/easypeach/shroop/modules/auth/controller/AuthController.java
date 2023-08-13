package com.easypeach.shroop.modules.auth.controller;

import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.dto.response.SignUpCompletedResponse;
import com.easypeach.shroop.modules.auth.service.AuthService;
import com.easypeach.shroop.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<SignUpCompletedResponse> signUp(@Validated @RequestBody SignUpRequest signUpRequest){
        Long memberId = authService.saveMember(signUpRequest);
        return ResponseEntity.ok(new SignUpCompletedResponse(memberId));
    }
}
