package com.fooddelivery.payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.payment_service.dto.CreatePaymentRequest;
import com.fooddelivery.payment_service.dto.PaymentResponse;
import com.fooddelivery.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

	
	private final PaymentService service;
	
	@PostMapping
	public ResponseEntity<PaymentResponse> pay(@RequestHeader("X-USER-ID") String userid,@RequestBody CreatePaymentRequest req){
		return ResponseEntity.ok(service.create(userid, req));
	}
}
