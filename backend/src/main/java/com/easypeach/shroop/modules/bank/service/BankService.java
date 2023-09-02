package com.easypeach.shroop.modules.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.bank.domain.Bank;
import com.easypeach.shroop.modules.bank.domain.BankRepository;
import com.easypeach.shroop.modules.bank.dto.LinkBankRequest;
import com.easypeach.shroop.modules.bank.exception.MinusMoneyException;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BankService {
	private final BankRepository bankRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void subtractMoney(final Long point, final Member member) {

		Bank bank = bankRepository.getByAccount(member.getAccount());
		if (bank.getMoney() >= point) {
			bank.subtractMoney(point);
		} else {
			throw new MinusMoneyException("충전에 실패하셨습니다.");
		}
	}

	@Transactional
	public void addPoint(final Long point, final Member member) {
		Bank bank = bankRepository.getByAccount(member.getAccount());
		bank.addMoney(point);
	}

	@Transactional
	public void linkingAccount(final LinkBankRequest linkBankRequest, final Member member) {
		// member에 계좌를 넣어주고, bank에 새로운 계좌 등록
		Member foundMember = memberRepository.getById(member.getId());
		foundMember.updateAccount(linkBankRequest.getAccount());
		Bank bank = Bank.createBank(linkBankRequest.getName(), linkBankRequest.getAccount());
		bankRepository.save(bank);
	}
}
