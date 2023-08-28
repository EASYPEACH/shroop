package com.easypeach.shroop.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroop.modules.auth.domain.PhoneAuth;
import com.easypeach.shroop.modules.auth.exception.PhoneAuthNotExistException;

public interface PhoneAuthRepository extends JpaRepository<PhoneAuth, Long> {
	default PhoneAuth getById(final Long id) {
		return findById(id).orElseThrow(() -> new PhoneAuthNotExistException("인증 정보를 찾을 수 없습니다"));
	}
}
