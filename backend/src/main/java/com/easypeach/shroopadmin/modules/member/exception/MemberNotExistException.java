package com.easypeach.shroopadmin.modules.member.exception;

public class MemberNotExistException extends RuntimeException {
	public MemberNotExistException() {
		super();
	}

	public MemberNotExistException(String msg) {
		super(msg);
	}
}
