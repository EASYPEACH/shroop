package com.easypeach.shroop.modules.auth.service;

import com.easypeach.shroop.modules.auth.dto.request.SignInRequest;
import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.dto.response.AccessTokenResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Member saveMember(SignUpRequest signUpRequest) {
        Member member = Member.createMember(signUpRequest.getLoginId()
                , signUpRequest.getPassword()
                , signUpRequest.getNickname()
                , signUpRequest.getPhoneNumber()
                , Role.ROLE_USER
                , 0L);

        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public AccessTokenResponse login(SignInRequest signInRequest){
        String loginId = signInRequest.getLoginId();
        String password = signInRequest.getPassword();
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(()->new RuntimeException());

        if(!member.getPassword().equals(password)){
            throw new RuntimeException("비밀번호 불일치");
        }

        AccessTokenResponse accessTokenResponse
                = new AccessTokenResponse(jwtTokenProvider.createToken(member.getLoginId(),member.getNickname(),member.getRole()));
        return accessTokenResponse;
    }
}
