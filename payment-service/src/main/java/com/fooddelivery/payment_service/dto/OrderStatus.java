package com.fooddelivery.payment_service.dto;

public enum OrderStatus {
	
	CREATED,
	PAYMENT_PENDING,
	PAID,
	ACCEPTED_BY_RESTAURANT,
	PREPARING,
	OUT_FOR_DELIVERY,
	DELIVERED,
	CANCELLED,
	REFUND_INITIATED,
	REFUNDED

}
