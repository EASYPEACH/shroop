package com.easypeach.shroop.modules.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
}
