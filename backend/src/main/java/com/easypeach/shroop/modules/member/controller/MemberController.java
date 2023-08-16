package com.easypeach.shroop.modules.member.controller;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.auth.support.LoginMemberId;
import com.easypeach.shroop.modules.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberController {

    @PostMapping(value = "/test")
    public String test(@LoginMemberId Long memberId, @LoginMember Member member){

        return "ok";
    }
}
