package com.fooddelivery.payment_service.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {

	private long orderId;
	
	private double amount;
}
