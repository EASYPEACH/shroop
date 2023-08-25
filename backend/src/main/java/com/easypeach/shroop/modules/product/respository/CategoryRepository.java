package com.easypeach.shroop.modules.product.respository;

import com.easypeach.shroop.modules.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
