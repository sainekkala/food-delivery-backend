package com.fooddelivery.order_service.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "restaurant-service",url = "http://localhost:8083")
public interface RestaurantClient {
	
	@GetMapping("/api/restaurants/{id}")
	Object getRestaurent (@PathVariable long id);

}
