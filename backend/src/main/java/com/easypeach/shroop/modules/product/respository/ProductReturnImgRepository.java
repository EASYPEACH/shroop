package com.easypeach.shroop.modules.product.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.easypeach.shroop.modules.product.domain.ProductReturnImg;
import com.easypeach.shroop.modules.product.dto.response.ProductReturnImgResponse;

public interface ProductReturnImgRepository extends JpaRepository<ProductReturnImg, Long> {

	@Query("select new com.easypeach.shroop.modules.product.dto.response.ProductReturnImgResponse(pri.id, pri.imgUrl) from ProductReturnImg pri where pri.productReturn.id = :productReturnId")
	List<ProductReturnImgResponse> findByProductReturnId(@Param("productReturnId") Long productReturnId);
}
