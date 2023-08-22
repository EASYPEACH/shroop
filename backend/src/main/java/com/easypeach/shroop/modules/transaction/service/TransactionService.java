package com.easypeach.shroop.modules.transaction.service;

import org.springframework.stereotype.Service;

import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;

	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
}
