package com.easypeach.shroopadmin.modules.product.exception;

public class DuplicateCategoryException extends RuntimeException {
	public DuplicateCategoryException() {
		super();
	}

	public DuplicateCategoryException(String msg) {
		super(msg);
	}
}
