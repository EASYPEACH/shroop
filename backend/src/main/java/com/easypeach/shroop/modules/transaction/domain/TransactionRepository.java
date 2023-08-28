package com.easypeach.shroop.modules.transaction.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easypeach.shroop.modules.transaction.exception.TransactionNotExistException;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Optional<Transaction> findByProductId(Long productId);

	default Transaction getByProductId(Long productId) {
		return findByProductId(productId).orElseThrow(
			() -> TransactionNotExistException.transactionNotExistException());
	}
}
