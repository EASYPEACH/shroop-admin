package com.easypeach.shroopadmin.modules.product.exception;

public class CategoryNotExistException extends RuntimeException {

	public CategoryNotExistException() {
		super();
	}

	public CategoryNotExistException(String msg) {
		super(msg);
	}
}
