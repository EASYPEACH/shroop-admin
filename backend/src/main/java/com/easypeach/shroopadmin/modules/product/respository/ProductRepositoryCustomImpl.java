package com.easypeach.shroopadmin.modules.product.respository;

import static com.easypeach.shroopadmin.modules.product.domain.QProduct.*;
import static com.easypeach.shroopadmin.modules.transaction.domain.QTransaction.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.easypeach.shroopadmin.modules.product.dto.response.ProductOneImgResponse;
import com.easypeach.shroopadmin.modules.transaction.domain.TransactionStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<ProductOneImgResponse> searchProduct(String searchWord, String transactionStatus, Pageable pageable) {

		QueryResults<ProductOneImgResponse> result = queryFactory.select(
				Projections.constructor(ProductOneImgResponse.class,
					product.id,
					transaction.status,
					product.title,
					product.seller.id,
					product.category.name,
					product.price,
					product.isCheckedDeliveryFee,
					product.content,
					product.createDate))
			.from(product)
			.leftJoin(product.category)
			.leftJoin(product.seller)
			.leftJoin(transaction)
			.on(transaction.product.id.eq(product.id))
			.where(searchWordContains(searchWord), searchTransactionStatus(transactionStatus))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(product.createDate.desc())
			.fetchResults();

		List<ProductOneImgResponse> content = result.getResults();
		Long total = result.getTotal();

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression searchWordContains(String searchWord) {
		if (searchWord.isBlank()) {
			return null;
		}
		return product.title.contains(searchWord)
			.or(product.category.name.contains(searchWord));
	}

	private BooleanExpression searchTransactionStatus(String transactionStatus) {
		TransactionStatus returnTransactionStatus = null;
		if (transactionStatus.equals("판매중")) {
			return transaction.status.isNull();
		}

		if (checkTransactionStatus(TransactionStatus.PURCHASE_REQUEST, transactionStatus)) {
			returnTransactionStatus = TransactionStatus.PURCHASE_REQUEST;
		} else if (checkTransactionStatus(TransactionStatus.PURCHASE_CONFIRM, transactionStatus)) {
			returnTransactionStatus = TransactionStatus.PURCHASE_CONFIRM;
		} else if (checkTransactionStatus(TransactionStatus.RETURN_REQUEST, transactionStatus)) {
			returnTransactionStatus = TransactionStatus.RETURN_REQUEST;
		} else if (checkTransactionStatus(TransactionStatus.RETURN_COMPLETE, transactionStatus)) {
			returnTransactionStatus = TransactionStatus.RETURN_COMPLETE;
		} else if (checkTransactionStatus(TransactionStatus.COMPLETE, transactionStatus)) {
			returnTransactionStatus = TransactionStatus.COMPLETE;
		}
		if (returnTransactionStatus == null) {
			return null;
		}
		return transaction.status.eq(returnTransactionStatus);
	}

	public boolean checkTransactionStatus(TransactionStatus status, String searchStatus) {
		return status.getName().equals(searchStatus);
	}

}
