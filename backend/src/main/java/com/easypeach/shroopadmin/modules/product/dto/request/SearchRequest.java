package com.easypeach.shroopadmin.modules.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SearchRequest {

	private String searchWord;
	private String transactionStatus;


	public SearchRequest(String searchWord, String transactionStatus) {
		
		this.searchWord = searchWord;
		this.transactionStatus = transactionStatus;
	}
}