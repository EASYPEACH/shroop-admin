package com.easypeach.shroopadmin.modules.product.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroopadmin.modules.global.request.SearchRequest;
import com.easypeach.shroopadmin.modules.global.response.BasicResponse;
import com.easypeach.shroopadmin.modules.product.dto.request.CategoryRequest;
import com.easypeach.shroopadmin.modules.product.dto.response.CategoryResponse;
import com.easypeach.shroopadmin.modules.product.dto.response.PageCategoryResponse;
import com.easypeach.shroopadmin.modules.product.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<PageCategoryResponse> searchFindAll(final SearchRequest searchRequest,
		final Pageable pageable) {

		PageCategoryResponse response = categoryService.searchFindAll(searchRequest, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryResponse>> getCategoryList() {
		List<CategoryResponse> categoryResponse = categoryService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
	}


	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryResponse> findById(final @PathVariable Long categoryId) {

		CategoryResponse response = categoryService.findById(categoryId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<BasicResponse> create(final @RequestBody CategoryRequest categoryRequest) {

		categoryService.create(categoryRequest);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("카테고리가 추가되었습니다."));
	}

	@PatchMapping("/{categoryId}")
	public ResponseEntity<BasicResponse> update(final @PathVariable Long categoryId,
		final @RequestBody CategoryRequest categoryRequest) {
		categoryService.update(categoryId, categoryRequest);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("카테고리가 수정되었습니다."));
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<BasicResponse> delete(final @PathVariable Long categoryId) {
		categoryService.deleteById(categoryId);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("카테고리가 삭제되었습니다."));
	}

}
