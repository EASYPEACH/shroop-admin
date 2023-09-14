package com.easypeach.shroopadmin.modules.transaction.domain;

public enum TransactionStatus {

	PURCHASE_REQUEST("구매신청"),
	PURCHASE_CONFIRM("거래완료"),
	RETURN_REQUEST("반품신청"),
	RETURN_REFUSE("반품거부"),
	RETURN_COMPLETE("반품완료"),
	COMPLETE("배송완료"),
	MEDIATE_REQUEST("중재신청");

	private final String name;

	TransactionStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
