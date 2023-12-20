package com.ecommerce.product.exception.handler;

import com.ecommerce.product.payloads.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.product.exception.CategoryNotFoundException;
import com.ecommerce.product.exception.ProductNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class, Exception.class})
	public ResponseEntity<?> NotFoundExceptionHandler(Exception exception){
		return ResponseEntity.ok(CommonResponse.builder().message(exception.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build());
	}
}
