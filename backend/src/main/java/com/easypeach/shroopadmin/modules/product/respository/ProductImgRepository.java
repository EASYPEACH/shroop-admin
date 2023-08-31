package com.easypeach.shroopadmin.modules.product.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easypeach.shroopadmin.modules.product.domain.ProductImg;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
}
