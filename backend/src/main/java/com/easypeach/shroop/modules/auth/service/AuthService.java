package com.easypeach.shroop.modules.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.infra.phone.NaverPhoneAuthService;
import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.dto.response.LoginCheckResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.member.domain.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final NaverPhoneAuthService naverPhoneAuthService;

	public Member saveMember(SignUpRequest signUpRequest) {
		Member member = Member.createMember(signUpRequest.getLoginId()
			, passwordEncoder.encode(signUpRequest.getPassword())
			, signUpRequest.getNickname()
			, signUpRequest.getPhoneNumber()
			, Role.ROLE_USER
			, 0L);

		return memberRepository.save(member);
	}

	public void saveTestMember(String loginId, String password, String nickname, String phoneNumber) {
		Member member = Member.createMember(loginId
			, passwordEncoder.encode(password)
			, nickname
			, phoneNumber
			, Role.ROLE_USER
			, 0L);
		memberRepository.save(member);
		return;
	}

	public LoginCheckResponse checkLogin(Member member) {
		if (member == null) {
			return new LoginCheckResponse(-1L, "", "", false);
		}

		return new LoginCheckResponse(
			member.getId(),
			member.getLoginId(),
			member.getNickname(),
			true);

	}
}
