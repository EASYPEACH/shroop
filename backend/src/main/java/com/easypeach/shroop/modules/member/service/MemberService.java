package com.easypeach.shroop.modules.member.service;

import org.springframework.stereotype.Service;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public Member findById(final Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다"));
	}
}
