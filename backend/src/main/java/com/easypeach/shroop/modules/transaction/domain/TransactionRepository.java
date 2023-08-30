package com.easypeach.shroop.modules.transaction.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.transaction.exception.TransactionNotExistException;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Transaction findByProduct(Product product);

	Optional<Transaction> findByProductId(Long productId);

	List<Transaction> findAllByBuyer(Member buyer);

	default Transaction getByProductId(Long productId) {
		return findByProductId(productId).orElseThrow(
			() -> TransactionNotExistException.transactionNotExistException());
	}

}
