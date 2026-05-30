package com.fooddelivery.food_service.dto;

import lombok.Data;

@Data
public class CreateFoodRequest {
	
	private String name;
	
	private String description;
	
	private double price;
	
	private String category;
	
	private long restaurantId;

}
