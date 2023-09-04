package com.easypeach.shroop.modules.bank.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.bank.domain.Bank;
import com.easypeach.shroop.modules.bank.domain.BankRepository;
import com.easypeach.shroop.modules.bank.dto.LinkBankRequest;
import com.easypeach.shroop.modules.bank.exception.AccountMismatchException;
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
	private final PasswordEncoder passwordEncoder;

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
	public void addMoney(final Long point, final Member member) {
		Bank bank = bankRepository.getByAccount(member.getAccount());
		bank.addMoney(point);
	}

	@Transactional
	public void linkingAccount(final LinkBankRequest linkBankRequest, final Member member) {
		String EnteredAccount = linkBankRequest.getAccount();
		String EnteredPassword = linkBankRequest.getPassword();
		String encodedEnteredPassword = passwordEncoder.encode(EnteredPassword);

		Member foundMember = memberRepository.getById(member.getId());
		String encodedBankPassword = foundMember.getPassword();
		String BankAccount = foundMember.getAccount();

		boolean isMatchedPassword = passwordEncoder.matches(encodedEnteredPassword, encodedBankPassword);
		boolean isMatchedAccount = EnteredAccount.matches(BankAccount);

		if (isMatchedPassword && isMatchedAccount) {
			foundMember.updateAccount(linkBankRequest.getAccount());
		} else {
			throw new AccountMismatchException("계좌번호가 일치하지 않습니다.");
		}
	}

	public void creatingAccount(LinkBankRequest linkBankRequest) {
		String encodedPassword = passwordEncoder.encode(linkBankRequest.getPassword());
		Bank bank = Bank.createBank(linkBankRequest.getName(), linkBankRequest.getAccount(), encodedPassword);
		bankRepository.save(bank);
	}
}
