package com.fooddelivery.food_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.food_service.dto.CreateFoodRequest;
import com.fooddelivery.food_service.entity.FoodItem;
import com.fooddelivery.food_service.service.FoodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {
	
	private final FoodService service;
	
	@PostMapping
	public ResponseEntity<FoodItem> create(@RequestBody CreateFoodRequest req,@RequestHeader("X-ROLE") String role){
		if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new RuntimeException("Only ADMIN can add food");
        }
		
		return ResponseEntity.ok(service.create(req));
	}
	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<FoodItem>> getByRestaurant(@PathVariable long restaurantId){
		return ResponseEntity.ok(service.getByRestaurant(restaurantId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable long id, @RequestHeader("X-ROLE") String role){
		if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new RuntimeException("Only ADMIN can delete food");
        }
		
		return ResponseEntity.ok(service.delete(id));
		
	}
	

}
