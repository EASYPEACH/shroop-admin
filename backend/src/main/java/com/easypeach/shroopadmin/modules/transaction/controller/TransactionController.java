package com.easypeach.shroopadmin.modules.transaction.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroopadmin.modules.global.request.SearchRequest;
import com.easypeach.shroopadmin.modules.transaction.dto.response.TransactionResponse;
import com.easypeach.shroopadmin.modules.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/transactions")
@RestController
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	@GetMapping
	public ResponseEntity<Page<TransactionResponse>> searchFindAll(final SearchRequest searchRequest,
		final Pageable pageable) {
		Page<TransactionResponse> response = transactionService.searchFindAll(searchRequest, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}

