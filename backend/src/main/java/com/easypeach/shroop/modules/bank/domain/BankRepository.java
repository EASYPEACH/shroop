package com.easypeach.shroop.modules.bank.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroop.modules.bank.exception.BankNotExistException;

public interface BankRepository extends JpaRepository<Bank, Long> {

	Optional<Bank> findByAccount(String account);

	default Bank getByAccount(String account) {
		return findByAccount(account).orElseThrow(
			() -> BankNotExistException.bankNotExistException());
	}
}
