package com.easypeach.shroopadmin.modules.product.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easypeach.shroopadmin.modules.product.domain.Product;
import com.easypeach.shroopadmin.modules.product.domain.ProductImg;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
	List<ProductImg> findAllByProduct(Product product);
}
