package com.easypeach.shroop.modules.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.easypeach.shroop.modules.auth.domain.SecurityMember;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service("securityUserDetailsService")
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String loginId) {
		Member member = memberRepository.getByLoginId(loginId);

		return new SecurityMember(member);
	}

}
