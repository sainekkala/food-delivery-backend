package com.fooddelivery.order_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateOrderRequest {
	
	private long restaurantId;
	
	private List<Long> foodIds;

}
