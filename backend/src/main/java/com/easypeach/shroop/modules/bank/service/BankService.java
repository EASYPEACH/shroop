package com.easypeach.shroop.modules.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.bank.domain.Bank;
import com.easypeach.shroop.modules.bank.domain.BankRepository;
import com.easypeach.shroop.modules.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BankService {
	private final BankRepository bankRepository;

	@Transactional
	public void subtractMoney(Long point, Member member) {
		Bank bank = bankRepository.getByAccount(member.getAccount());
		bank.subtractMoney(point);
	}

	@Transactional
	public void addPoint(Long point, Member member) {
		Bank bank = bankRepository.getByAccount(member.getAccount());
		bank.addMoney(point);
	}
}
