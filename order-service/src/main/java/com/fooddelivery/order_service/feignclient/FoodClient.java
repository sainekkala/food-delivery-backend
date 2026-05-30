package com.fooddelivery.order_service.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fooddelivery.order_service.dto.FoodResponse;

@FeignClient(name = "food-service",url = "http://localhost:8084")
public interface FoodClient {
    
	@GetMapping("/api/foods/restaurant/{restaurantId}")
	List<FoodResponse> getFoods(@PathVariable long restaurantId);
}
