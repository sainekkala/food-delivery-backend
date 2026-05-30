package com.fooddelivery.order_service.dto;

import lombok.Data;

@Data
public class FoodResponse {
	
	private long id;
	
	private String name;
	
	private double price;
	
	private long restaurantId;

}
