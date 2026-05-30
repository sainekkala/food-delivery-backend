package com.fooddelivery.payment_service.dto;

import org.springframework.stereotype.Component;

@Component
public class FakePaymentGateway {

	
	public boolean charge(double amount) {
		return Math.random() > 0.5;
	}
}
