package com.easypeach.shroop.modules.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.infra.phone.NaverPhoneAuthService;
import com.easypeach.shroop.modules.auth.domain.PhoneAuth;
import com.easypeach.shroop.modules.auth.dto.request.PhoneAuthRequest;
import com.easypeach.shroop.modules.auth.exception.PhoneAuthFailException;
import com.easypeach.shroop.modules.auth.repository.PhoneAuthRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhoneAuthService {
	private final PhoneAuthRepository phoneAuthRepository;
	private final NaverPhoneAuthService sms;

	@Transactional
	public Long sendAuthNumber(String phoneNumber) {
		PhoneAuth phoneAuth = PhoneAuth.createPhoneAuth(phoneNumber);
		phoneAuthRepository.save(phoneAuth);
		//        sms.sendSms(phoneNumber,phoneAuth.getAuthNumber());
		log.info("인증 번호 전달 {}", phoneAuth.getAuthNumber()); // 인증 번호 로그로 전달
		return phoneAuth.getId();
	}

	@Transactional
	public void checkPhoneAuthNumber(PhoneAuthRequest phoneAuthRequest) {
		Long phoneAuthId = phoneAuthRequest.getUuid();
		PhoneAuth phoneAuth = phoneAuthRepository.getById(phoneAuthId);

		if (!phoneAuth.getPhoneNumber().equals(phoneAuthRequest.getPhoneNumber())) {
			throw new PhoneAuthFailException("잘못된 인증 정보입니다");
		}

		if (!phoneAuth.getAuthNumber().equals(phoneAuthRequest.getPhoneAuthNumber())) {
			throw new PhoneAuthFailException("인증 번호를 다시 확인해주세요");
		}

		phoneAuth.updateAuthResult(true);
	}
}
