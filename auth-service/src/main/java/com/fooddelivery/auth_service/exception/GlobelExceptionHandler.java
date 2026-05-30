package com.fooddelivery.auth_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobelExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAll(Exception e){
		return ResponseEntity.status(500).body(java.util.Map.of("error","Internal server error"));
	}

}
