package com.easypeach.shroopadmin.modules.transaction.dto.response;

import java.time.LocalDateTime;

import com.easypeach.shroopadmin.modules.transaction.domain.Transaction;
import com.easypeach.shroopadmin.modules.transaction.domain.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionResponse {

	private Long id;

	private Long buyerId;

	private String buyerLoginId;

	private Long sellerId;

	private String sellerLoginId;

	private Long productId;

	private String productTitle;

	private TransactionStatus status;

	private LocalDateTime createDate;

	public TransactionResponse(Transaction transaction) {
		this.id = transaction.getId();
		this.buyerId = transaction.getBuyer().getId();
		this.buyerLoginId = transaction.getBuyer().getLoginId();
		this.sellerId = transaction.getSeller().getId();
		this.sellerLoginId = transaction.getSeller().getLoginId();
		this.productId = transaction.getProduct().getId();
		this.productTitle = transaction.getProduct().getTitle();
		this.status = transaction.getStatus();
		this.createDate = transaction.getCreateDate();
	}

}
