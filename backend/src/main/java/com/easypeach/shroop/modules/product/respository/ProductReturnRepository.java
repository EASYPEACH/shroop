package com.easypeach.shroop.modules.product.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroop.modules.product.domain.ProductReturn;

public interface ProductReturnRepository extends JpaRepository<ProductReturn, Long> {
	
}
