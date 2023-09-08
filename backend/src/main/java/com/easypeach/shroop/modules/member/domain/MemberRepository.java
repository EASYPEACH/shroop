package com.easypeach.shroop.modules.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easypeach.shroop.modules.member.exception.NoSuchMemberException;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByLoginId(String loginId);

	boolean existsByNickname(String nickname);

	Optional<Member> findByLoginId(String loginId);

	Optional<Member> findByNickname(String nickname);

	Optional<Member> findByPhoneNumber(String phoneNumber);

	default Member getByLoginId(final String LoginId) {
		return findByLoginId(LoginId)
			.orElseThrow(NoSuchMemberException::new);
	}

	default Member getById(final Long id) {
		return findById(id)
			.orElseThrow(NoSuchMemberException::new);
	}
}
