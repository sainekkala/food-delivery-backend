package com.fooddelivery.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {

	private long paymentId;
	
	private String status;
}
