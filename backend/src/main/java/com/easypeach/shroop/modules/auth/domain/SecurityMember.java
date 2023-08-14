package com.easypeach.shroop.modules.auth.domain;

import com.easypeach.shroop.modules.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class SecurityMember extends User {
    @Getter
    private final Member member;

    public SecurityMember(Member member){
        super(member.getLoginId(),member.getPassword(), List.of(new SimpleGrantedAuthority(member.getRole().name())));
        this.member = member;
    }
}
