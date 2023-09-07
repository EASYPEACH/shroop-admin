package com.easypeach.shroopadmin.modules.product.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.easypeach.shroopadmin.modules.product.dto.response.ProductOneImgResponse;

public interface ProductRepositoryCustom {
	Page<ProductOneImgResponse> searchProduct(String searchWord, String transactionStatus,
		Pageable pageable);
}
