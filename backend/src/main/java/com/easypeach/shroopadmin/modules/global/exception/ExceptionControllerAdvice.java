package com.easypeach.shroopadmin.modules.global.exception;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easypeach.shroopadmin.modules.auth.exception.InvalidTokenException;
import com.easypeach.shroopadmin.modules.auth.exception.NotAuthrizedUserException;
import com.easypeach.shroopadmin.modules.global.exception.dto.ErrorResponse;

import com.easypeach.shroopadmin.modules.product.exception.CategoryDeleteException;
import com.easypeach.shroopadmin.modules.product.exception.DuplicateCategoryException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
	@ExceptionHandler({
		CategoryDeleteException.class,
		DuplicateCategoryException.class
	})
	public ResponseEntity<ErrorResponse> handleNotExistException(final RuntimeException e) {
		String errorMessage = e.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return ResponseEntity.status(400).body(errorResponse);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(final Exception e) {


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		final MethodArgumentNotValidException e) {
		FieldError firstFieldError = e.getFieldErrors().get(0);
		String errorMessage = firstFieldError.getDefaultMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);

		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler({
		InvalidTokenException.class
	})
	public ResponseEntity<ErrorResponse> handleNotExistException(final RuntimeException e) {
		String errorMessage = e.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return ResponseEntity.status(404).body(errorResponse);
	}

	@ExceptionHandler({
		NotAuthrizedUserException.class,
	})
	public ResponseEntity<ErrorResponse> handleAuthorizationExceoption(final RuntimeException e) {
		String errorMessage = e.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return ResponseEntity.status(403).body(errorResponse);
	}

	@ExceptionHandler({

	})
	public ResponseEntity<ErrorResponse> handleAuthException(final RuntimeException e) {
		String errorMessage = e.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(final Exception e) {
		String errorMessage = e.getMessage();
		log.error("ERROR {}", e);
		ErrorResponse errorResponse = new ErrorResponse("내부 서버에 문제가 발생하여 확인 중 입니다");
		return ResponseEntity.internalServerError().body(errorResponse);
	}


}

