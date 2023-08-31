package com.easypeach.shroopadmin.modules.product.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easypeach.shroopadmin.modules.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
