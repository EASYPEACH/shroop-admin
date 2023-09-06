package com.easypeach.shroopadmin.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.easypeach.shroopadmin.modules.product.domain.Product;
import com.easypeach.shroopadmin.modules.product.respository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public List<Product> findByCategoryId(final Long categoryId) {
		List<Product> productList = productRepository.findByCategoryId(categoryId);

		return productList;
	}

}
