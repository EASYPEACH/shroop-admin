package com.easypeach.shroopadmin.modules.product.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroopadmin.modules.product.dto.request.SearchRequest;
import com.easypeach.shroopadmin.modules.global.response.BasicResponse;
import com.easypeach.shroopadmin.modules.product.dto.request.ProductRequest;
import com.easypeach.shroopadmin.modules.product.dto.response.ProductCreatedResponse;
import com.easypeach.shroopadmin.modules.product.dto.response.ProductOneImgResponse;
import com.easypeach.shroopadmin.modules.product.dto.response.ProductResponse;
import com.easypeach.shroopadmin.modules.product.service.ProductImgService;
import com.easypeach.shroopadmin.modules.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	private final ProductImgService productImgService;

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProduct (@PathVariable Long productId) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.getProductInfo(productId));
	}

	@PostMapping
	public ResponseEntity<ProductCreatedResponse> saveProduct(
		@RequestPart(value = "productImgList") List<MultipartFile> productImgList,
		@RequestPart(value = "defectImgList", required = false) List<MultipartFile> defectImgList,
		@Validated @RequestPart ProductRequest productRequest) {
		productImgService.checkImgLength(productImgList);
		Long productId = productService.saveProduct(1L, productRequest);
		productImgService.saveProductImg(productImgList, defectImgList, productId, productRequest.getIsDefect());
		return ResponseEntity.status(HttpStatus.OK).body(new ProductCreatedResponse(productId));
	}

	@PatchMapping
	public ResponseEntity<ProductCreatedResponse> updateProduct(
		@RequestPart(value = "memberId") Long memberId,
		@RequestPart(value = "productId") Long productId,
		@RequestPart(value = "productImgList") List<MultipartFile> productImgList,
		@RequestPart(value = "defectImgList", required = false) List<MultipartFile> defectImgList,
		@RequestPart ProductRequest productRequest) {
		productImgService.checkImgLength(productImgList);
		Long updateProductId = productService.updateProduct(memberId, productId, productRequest);
		productImgService.updateProductImgList(productImgList, defectImgList, productId,
			productRequest.getIsDefect());
		return ResponseEntity.status(HttpStatus.OK).body(new ProductCreatedResponse(updateProductId));
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<BasicResponse> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("삭제가 완료되었습니다"));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<ProductOneImgResponse>> searchProduct(
		final SearchRequest searchRequest,
		final Pageable pageable) {
		Page<ProductOneImgResponse>  response = productService.searchProduct(searchRequest.getSearchWord(), searchRequest.getTransactionStatus(),pageable);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
