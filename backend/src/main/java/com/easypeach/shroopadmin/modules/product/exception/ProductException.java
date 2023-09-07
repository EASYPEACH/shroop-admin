package com.easypeach.shroopadmin.modules.product.exception;

import com.easypeach.shroopadmin.modules.transaction.domain.TransactionStatus;

public class ProductException extends RuntimeException {
	private ProductException(String message) {
		super(message);
	}

	public static ProductException notExistProduct() {
		return new ProductException("존재하지 않는 상품입니다.");
	}

	public static ProductException notAuthorizationToUpdate() {
		return new ProductException("수정 권한이 없습니다.");
	}

	public static ProductException notAuthorizationToDelete() {
		return new ProductException("삭제 권한이 없습니다.");
	}

	public static ProductException notStatusDelete(TransactionStatus status) {
		return new ProductException(status.getName() + "상태에서는 삭제할 수 없습니다.");
	}

}
