package com.easypeach.shroopadmin.modules.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequest {

	private String name;

	public CategoryRequest(String name) {
		this.name = name;
	}
}
