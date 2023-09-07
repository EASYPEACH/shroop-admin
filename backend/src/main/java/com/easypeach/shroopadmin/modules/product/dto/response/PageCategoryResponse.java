package com.easypeach.shroopadmin.modules.product.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PageCategoryResponse {

	private int totalCount;

	private List<CategoryResponse> responseList;

	public PageCategoryResponse(int totalCount, List<CategoryResponse> responseList) {
		this.totalCount = totalCount;
		this.responseList = responseList;
	}
}
