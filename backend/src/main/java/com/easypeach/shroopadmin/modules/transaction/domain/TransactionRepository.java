package com.easypeach.shroopadmin.modules.transaction.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroopadmin.modules.product.domain.Product;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Transaction findByProduct(Product product);
}
