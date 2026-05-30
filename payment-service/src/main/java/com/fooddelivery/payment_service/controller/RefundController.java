package com.fooddelivery.payment_service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.payment_service.service.RefundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/internal/refunds")
@RequiredArgsConstructor
public class RefundController {

	private final RefundService service;
	
	@PostMapping("/{orderId}")
	public void refund(@PathVariable long orderId) {
		
		service.initiateRefund(orderId);
	}
}
