package com.easypeach.shroop.modules.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.member.domain.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void saveMember(SignUpRequest signUpRequest) {
		Member member = Member.createMember(signUpRequest.getLoginId()
			, passwordEncoder.encode(signUpRequest.getPassword())
			, signUpRequest.getNickname()
			, signUpRequest.getPhoneNumber()
			, Role.ROLE_USER
			, 0L);

		Member savedMember = memberRepository.save(member);

	}
}
