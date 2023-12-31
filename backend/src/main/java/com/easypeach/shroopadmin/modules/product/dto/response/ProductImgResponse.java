package com.easypeach.shroopadmin.modules.product.dto.response;

import com.easypeach.shroopadmin.modules.product.domain.ProductImg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductImgResponse {
	private Long id;
	private String productImgUrl;
	private Boolean isDefect;

	public ProductImgResponse(ProductImg productImg) {
		this.id = productImg.getId();
		this.productImgUrl = productImg.getProductImgUrl();
		this.isDefect = productImg.getIsDefect();
	}

}
