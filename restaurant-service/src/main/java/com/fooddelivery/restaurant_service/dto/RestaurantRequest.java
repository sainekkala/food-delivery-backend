package com.fooddelivery.restaurant_service.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {

	private String name;
	
	private String description;
	
	private String cuisine;
	
	private String city;
	
	private LocalTime openTime;
	
	private LocalTime closeTime;
	
}
