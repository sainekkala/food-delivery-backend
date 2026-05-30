package com.fooddelivery.payment_service.dto;

import org.springframework.stereotype.Component;

@Component
public class FakeRefund {
	
	public boolean refund(double amound) {
		return Math.random() > 0.2;
	}

}
