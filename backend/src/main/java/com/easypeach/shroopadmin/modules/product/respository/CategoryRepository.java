package com.easypeach.shroopadmin.modules.product.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroopadmin.modules.product.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Page<Category> findByNameContaining(String name, Pageable pageable);

}
