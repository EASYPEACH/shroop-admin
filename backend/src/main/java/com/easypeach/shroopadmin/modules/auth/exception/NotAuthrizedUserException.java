package com.easypeach.shroopadmin.modules.auth.exception;

public class NotAuthrizedUserException extends RuntimeException{
	public NotAuthrizedUserException(String message) {
		super(message);
	}
}
