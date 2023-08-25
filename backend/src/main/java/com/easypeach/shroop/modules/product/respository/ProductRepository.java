package com.easypeach.shroop.modules.product.respository;

import com.easypeach.shroop.modules.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
