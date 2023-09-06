package com.easypeach.shroopadmin.modules.product.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroopadmin.modules.product.domain.Category;
import com.easypeach.shroopadmin.modules.product.exception.CategoryNotExistException;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Page<Category> findByNameContaining(String name, Pageable pageable);

	boolean existsByName(String name);

	default Category getById(Long categoryId) {
		return findById(categoryId).orElseThrow(() -> new CategoryNotExistException("카테고리가 존재하지 않습니다."));
	}

}
