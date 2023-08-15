package com.easypeach.shroop.modules.auth.service;

import com.easypeach.shroop.modules.auth.dto.request.SignInRequest;
import com.easypeach.shroop.modules.auth.dto.request.SignUpRequest;
import com.easypeach.shroop.modules.auth.dto.response.AccessTokenResponse;
import com.easypeach.shroop.modules.auth.dto.response.SignUpCompletedResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.member.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public SignUpCompletedResponse saveMember(SignUpRequest signUpRequest) {
        Member member = Member.createMember(signUpRequest.getLoginId()
                , passwordEncoder.encode(signUpRequest.getPassword())
                , signUpRequest.getNickname()
                , signUpRequest.getPhoneNumber()
                , Role.ROLE_USER
                , 0L);

        Member savedMember = memberRepository.save(member);
        SignUpCompletedResponse signUpCompletedResponse = new SignUpCompletedResponse(member.getId(), member.getNickname());
        return signUpCompletedResponse;
    }

    public AccessTokenResponse login(SignInRequest signInRequest) {
        String loginId = signInRequest.getLoginId();
        String password = signInRequest.getPassword();
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalArgumentException("아이디 및 비밀번호가 맞지 않습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("아이디 및 비밀번호가 맞지 않습니다.");
        }

        AccessTokenResponse accessTokenResponse
                = new AccessTokenResponse(jwtTokenProvider.createToken(member.getLoginId(), member.getNickname(), member.getRole()));
        return accessTokenResponse;
    }
}
