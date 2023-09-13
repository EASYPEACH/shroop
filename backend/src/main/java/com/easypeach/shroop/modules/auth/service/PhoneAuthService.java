package com.easypeach.shroop.modules.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.infra.phone.NaverPhoneAuthService;
import com.easypeach.shroop.modules.auth.domain.PhoneAuth;
import com.easypeach.shroop.modules.auth.dto.request.PhoneAuthRequest;
import com.easypeach.shroop.modules.auth.dto.response.PhoneAuthUUID;
import com.easypeach.shroop.modules.auth.exception.AuthTimeOutException;
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
	private final PhoneAuthCountService phoneAuthCountService;
	private final NaverPhoneAuthService sms;

	@Value("${spring.auth.time}")
	private long authExpDateInMilliseconds;

	@Transactional
	public PhoneAuthUUID sendAuthNumber(String phoneNumber) {
		Date now = new Date();
		PhoneAuth phoneAuth = PhoneAuth.createPhoneAuth(phoneNumber,
			new Date(now.getTime() + authExpDateInMilliseconds));
		phoneAuthRepository.save(phoneAuth);
		String authNumber = phoneAuth.getAuthNumber();
		String message = "슈룹 인증 번호 : " + authNumber;

		sms.sendSms(phoneNumber, message); // 문자 발송
		log.info("인증 번호 전달 {}", authNumber); // 인증 번호 로그로 전달

		PhoneAuthUUID phoneAuthUUID = new PhoneAuthUUID(phoneAuth.getId(), authExpDateInMilliseconds / 1000);

		return phoneAuthUUID;
	}

	@Transactional
	public void checkPhoneAuthNumber(PhoneAuthRequest phoneAuthRequest) {
		Long phoneAuthId = phoneAuthRequest.getUuid();
		PhoneAuth phoneAuth = phoneAuthRepository.getById(phoneAuthId);

		Date now = new Date();
		if (!now.before(phoneAuth.getExpDate())) {
			throw new AuthTimeOutException("인증 시간을 초과하였습니다");
		}

		if (!phoneAuth.getPhoneNumber().equals(phoneAuthRequest.getPhoneNumber())) {
			throw new PhoneAuthFailException("휴대전화번호 및 인증 번호를 확인해주세요");
		}

		if (!phoneAuth.getAuthNumber().equals(phoneAuthRequest.getPhoneAuthNumber())) {
			phoneAuthCountService.addFailCount(phoneAuth.getId());
			throw new PhoneAuthFailException("인증 번호를 다시 확인해주세요");
		}

		phoneAuth.updateAuthResult(true);
	}

}
