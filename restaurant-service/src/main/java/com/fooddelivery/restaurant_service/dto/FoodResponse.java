package com.fooddelivery.restaurant_service.dto;

import lombok.Data;

@Data
public class FoodResponse {
	
	 private Long id;
	    private String name;
	    private String description;
	    private Double price;
	    private String category;
	    private Long restaurantId;

}
