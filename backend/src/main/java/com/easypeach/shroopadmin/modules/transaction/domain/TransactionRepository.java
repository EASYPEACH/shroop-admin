package com.easypeach.shroopadmin.modules.transaction.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypeach.shroopadmin.modules.product.domain.Product;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(value = "select t from Transaction t "
		+ "join fetch t.buyer b "
		+ "join fetch t.seller s "
		+ "join fetch t.product p "
		+ "where b.loginId like %:searchWord% or s.loginId like %:searchWord% or p.title like %:searchWord% "
		, countQuery = "select count(t) from Transaction t "
		+ "join t.buyer b "
		+ "join t.seller s "
		+ "join t.product p "
		+ "where b.loginId like %:searchWord% or s.loginId like %:searchWord% or p.title like %:searchWord% ")
	Page<Transaction> findAllFetchJoin(String searchWord, Pageable pageable);

	Transaction findByProduct(Product product);
}
