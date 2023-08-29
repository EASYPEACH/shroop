package com.easypeach.shroop.modules.product.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.exception.ProductException;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	default Product getById(Long id) {
		return findById(id).orElseThrow(() -> ProductException.notExistProduct());
	}

	Page<Product> findBySellerOrderByCreateDateDesc(Member seller, Pageable pageable);
}
