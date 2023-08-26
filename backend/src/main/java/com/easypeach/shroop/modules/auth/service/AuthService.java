package com.easypeach.shroop.modules.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.infra.phone.PhoneAuthService;
import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.exception.PhoneAuthFailException;
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
	private final PhoneAuthService phoneAuthService;

	public void checkPhoneAuthNumber(String loginId, String requestAuthToken) {

		Member findMember = memberRepository.getByLoginId(loginId);

		if (findMember.getPhoneAuthToken().equals(requestAuthToken)) {
			findMember.updateRole(Role.ROLE_USER);
		} else {
			throw new PhoneAuthFailException("인증 번호를 다시 확인해주세요");
		}

	}

	public void sendPhoneAuth(String loginId) {
		Member member = memberRepository.getByLoginId(loginId);
		log.info("인증 번호 전송 ----- {}", member.getPhoneAuthToken());
		// phoneAuthService.sendSms(member.getPhoneNumber(), member.getPhoneAuthToken());
	}

	public void saveMember(SignUpRequest signUpRequest) {
		Member member = Member.createMember(signUpRequest.getLoginId()
			, passwordEncoder.encode(signUpRequest.getPassword())
			, signUpRequest.getNickname()
			, signUpRequest.getPhoneNumber()
			, Role.ROLE_NOT_AUTH_USER
			, 0L);

		Member savedMember = memberRepository.save(member);
	}
}
