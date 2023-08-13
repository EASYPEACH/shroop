package com.easypeach.shroop.modules.auth.service;

import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public Long saveMember(SignUpRequest signUpRequest) {
        Member member = Member.createMember(signUpRequest.getLoginId()
                , signUpRequest.getPassword()
                , signUpRequest.getNickname()
                , signUpRequest.getPhoneNumber()
                , Role.USER
                , 0L);

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }
}
