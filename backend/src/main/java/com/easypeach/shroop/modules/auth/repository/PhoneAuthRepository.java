package com.easypeach.shroop.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroop.modules.auth.domain.PhoneAuth;
import com.easypeach.shroop.modules.auth.exception.PhoneAuthNotExistException;

public interface PhoneAuthRepository extends JpaRepository<PhoneAuth, Long> {
	default PhoneAuth getById(final Long id) {
		return findById(id).orElseThrow(() -> new PhoneAuthNotExistException("휴대전화번호 및 인증 번호를 확인해주세요"));
	}
}
