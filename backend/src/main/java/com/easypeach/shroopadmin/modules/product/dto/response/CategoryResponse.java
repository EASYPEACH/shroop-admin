package com.easypeach.shroopadmin.modules.product.dto.response;

import com.easypeach.shroopadmin.modules.product.domain.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponse {

	private Long id;

	private String name;

	public CategoryResponse(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
}
