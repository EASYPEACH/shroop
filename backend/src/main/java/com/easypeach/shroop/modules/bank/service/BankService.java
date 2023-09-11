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
import com.easypeach.shroop.modules.member.dto.request.PointRequest;

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
	public void subtractMoney(final PointRequest pointRequest, final Member member) {
		Long point = pointRequest.getPoint();
		Member foundMember = memberRepository.getById(member.getId());
		Bank bank = bankRepository.getByAccount(foundMember.getAccount());
		if (bank.getMoney() >= point) {
			bank.subtractMoney(point);
		} else {
			throw new MinusMoneyException("충전에 실패하셨습니다.");
		}
	}

	@Transactional
	public void addMoney(final PointRequest pointRequest, final Member member) {
		Long point = pointRequest.getPoint();
		Member foundMember = memberRepository.getById(member.getId());
		Bank bank = bankRepository.getByAccount(foundMember.getAccount());
		bank.addMoney(point);
	}

	@Transactional
	public void linkingAccount(final LinkBankRequest linkBankRequest, final Member member) {

		Member foundMember = memberRepository.getById(member.getId());

		boolean isAuthenticate = authenticateAccount(linkBankRequest);
		if (isAuthenticate) {
			foundMember.updateAccount(linkBankRequest.getAccount());
		} else {
			throw new AccountMismatchException("계좌 인증이 되지 않았습니다. 다시 확인해주세요.");

		}
	}

	@Transactional
	public void creatingAccount(final LinkBankRequest linkBankRequest) {
		String encodedPassword = passwordEncoder.encode(linkBankRequest.getPassword());
		Bank bank = Bank.createBank(linkBankRequest.getName(), linkBankRequest.getAccount(), encodedPassword);
		bankRepository.save(bank);
	}

	public boolean authenticateAccount(final LinkBankRequest linkBankRequest) {
		String enteredName = linkBankRequest.getName();
		String enteredAccount = linkBankRequest.getAccount();
		String enteredPassword = linkBankRequest.getPassword();

		Bank foundBank = bankRepository.getByAccount(enteredAccount);

		String bankName = foundBank.getName();
		String encodedBankPassword = foundBank.getPassword();

		boolean isMatchedPassword = passwordEncoder.matches(enteredPassword, encodedBankPassword);
		boolean isMatchedName = bankName.equals(enteredName);

		if (isMatchedPassword && isMatchedName) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean checkAccount(final Member member) {
		Member foundMember = memberRepository.getById(member.getId());
		return foundMember.getAccount() != null;
	}
}
