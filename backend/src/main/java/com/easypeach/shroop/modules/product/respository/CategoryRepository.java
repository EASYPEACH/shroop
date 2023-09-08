package com.easypeach.shroop.modules.product.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.exception.NoSuchCategoryException;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	default Category getById(final Long id) {
		return findById(id).orElseThrow(() -> new NoSuchCategoryException("존재하지 않는 카테고리 입니다"));
	}
}
