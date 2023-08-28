package com.easypeach.shroop.modules.transaction.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easypeach.shroop.modules.product.domain.Product;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Transaction findByProduct(Product product);
}
