package com.easypeach.shroop.modules.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.auth.domain.PhoneAuth;
import com.easypeach.shroop.modules.auth.exception.AuthFailCountException;
import com.easypeach.shroop.modules.auth.repository.PhoneAuthRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PhoneAuthCountService {

	private final PhoneAuthRepository phoneAuthRepository;
	private static final int failCount = 5;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addFailCount(Long phoneAuthId) {
		PhoneAuth phoneAuth = phoneAuthRepository.getById(phoneAuthId);
		phoneAuth.addFailCount();
		checkFailCount(phoneAuth);
	}

	public void checkFailCount(PhoneAuth phoneAuth) {
		if (phoneAuth.getFailCount() == failCount) {
			throw new AuthFailCountException("인증을 5회 실패하였습니다");
		}
	}
}
