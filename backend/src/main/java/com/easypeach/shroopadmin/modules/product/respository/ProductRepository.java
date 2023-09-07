package com.easypeach.shroopadmin.modules.product.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.easypeach.shroopadmin.modules.member.domain.Member;
import com.easypeach.shroopadmin.modules.product.domain.Product;
import com.easypeach.shroopadmin.modules.product.exception.ProductException;
import com.easypeach.shroopadmin.modules.report.domain.Report;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom  {


	List<Product> findByCategoryId(Long categoryId);
	default Product getById(Long id) {
		return findById(id).orElseThrow(() -> ProductException.notExistProduct());
	}

}
