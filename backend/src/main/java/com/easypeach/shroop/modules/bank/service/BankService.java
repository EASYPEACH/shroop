package com.easypeach.shroop.modules.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.bank.domain.Bank;
import com.easypeach.shroop.modules.bank.domain.BankRepository;
import com.easypeach.shroop.modules.bank.exception.MinusMoneyException;
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
	public void subtractMoney(final Long point, final Member member) {

		Bank bank = bankRepository.getByAccount(member.getAccount());
		if (bank.getMoney() >= point) {
			bank.subtractMoney(point);
		} else {
			throw MinusMoneyException.OverCharging();
		}
	}

	@Transactional
	public void addPoint(final Long point, final Member member) {
		Bank bank = bankRepository.getByAccount(member.getAccount());
		bank.addMoney(point);
	}
}
