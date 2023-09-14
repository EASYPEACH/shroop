package com.easypeach.shroop.modules.product.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.easypeach.shroop.modules.product.domain.ProductReturn;
import com.easypeach.shroop.modules.product.dto.response.ProductReturnResponse;
import com.easypeach.shroop.modules.product.exception.ProductException;

public interface ProductReturnRepository extends JpaRepository<ProductReturn, Long> {

	@Query("select new com.easypeach.shroop.modules.product.dto.response.ProductReturnResponse(pr.id, p.title, pr.content) from ProductReturn pr join fetch Product p on p.id = :productId")
	Optional<ProductReturnResponse> findByProductIdFetchJoin(@Param("productId") Long productId);

	default ProductReturnResponse getByProductId(Long productId) {
		return findByProductIdFetchJoin(productId).orElseThrow(ProductException::noSuchProductException);
	}
	
	void deleteByProductId(Long productID);

}
