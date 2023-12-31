package com.easypeach.shroopadmin.modules.product.dto.response;

import java.time.LocalDateTime;

import com.easypeach.shroopadmin.modules.product.domain.Product;
import com.easypeach.shroopadmin.modules.transaction.domain.TransactionStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOneImgResponse {

	private Long id;

	private TransactionStatus transactionStatus;

	private String title;

	private String categoryName;
	
	private Long sellerId;

	private Long price;

	private Boolean isCheckedDeliveryFee;

	private String content;

	private LocalDateTime createDate;


	public ProductOneImgResponse(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.categoryName = product.getCategory().getName();
		this.price = product.getPrice();
		this.isCheckedDeliveryFee = product.getIsCheckedDeliveryFee();
		this.content = product.getContent();
		this.createDate = product.getCreateDate();
		this.sellerId = product.getSeller().getId();
	}

	public ProductOneImgResponse(
		final Long id,
		final TransactionStatus transactionStatus,
		final String title,
		final Long sellerId,
		final String categoryName,
		final Long price,
		final Boolean isCheckedDeliveryFee,
		final String content,
		final LocalDateTime createDate
	) {
		this.id = id;
		this.transactionStatus = transactionStatus;
		this.title = title;
		this.sellerId = sellerId;
		this.categoryName = categoryName;
		this.price = price;
		this.isCheckedDeliveryFee = isCheckedDeliveryFee;
		this.content = content;
		this.createDate = createDate;
	}

}
