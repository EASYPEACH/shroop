package com.easypeach.shroop.modules.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByLoginId(String loginId);

	boolean existsByNickname(String nickname);

	Optional<Member> findByLoginId(String loginId);
}
