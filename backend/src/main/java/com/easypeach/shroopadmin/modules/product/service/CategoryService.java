package com.easypeach.shroopadmin.modules.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.easypeach.shroopadmin.modules.global.request.SearchRequest;
import com.easypeach.shroopadmin.modules.product.domain.Category;
import com.easypeach.shroopadmin.modules.product.dto.response.CategoryResponse;
import com.easypeach.shroopadmin.modules.product.dto.response.PageCategoryResponse;
import com.easypeach.shroopadmin.modules.product.respository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public PageCategoryResponse searchFindAll(final SearchRequest searchRequest, final Pageable pageable) {

		String searchWord = searchRequest.getSearchWord().isEmpty() ? "" : searchRequest.getSearchWord();

		Page<Category> categoryList = categoryRepository.findByNameContaining(searchWord, pageable);
		List<CategoryResponse> list = categoryList.stream().map(CategoryResponse::new).collect(Collectors.toList());
		int totalCount = (int)categoryList.getTotalElements();

		return new PageCategoryResponse(totalCount, list);
	}

	public CategoryResponse findById(final Long categoryId) {
		Category category = categoryRepository.getById(categoryId);

		return new CategoryResponse(category);
	}

}
