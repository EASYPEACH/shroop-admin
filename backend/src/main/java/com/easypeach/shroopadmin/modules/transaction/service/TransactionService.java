package com.easypeach.shroopadmin.modules.transaction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroopadmin.modules.global.request.SearchRequest;
import com.easypeach.shroopadmin.modules.transaction.domain.Transaction;
import com.easypeach.shroopadmin.modules.transaction.domain.TransactionRepository;
import com.easypeach.shroopadmin.modules.transaction.dto.response.TransactionResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TransactionService {

	private final TransactionRepository transactionRepository;

	public Page<TransactionResponse> searchFindAll(final SearchRequest searchRequest, final Pageable pageable) {
		Page<Transaction> transactions = transactionRepository.findAllFetchJoin(searchRequest.getSearchWord(),
			pageable);

		Page<TransactionResponse> list = transactions.map(TransactionResponse::new);

		return list;
	}

}
