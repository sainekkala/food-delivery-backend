package com.fooddelivery.restaurant_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.restaurant_service.dto.FoodResponse;
import com.fooddelivery.restaurant_service.dto.RestaurantRequest;
import com.fooddelivery.restaurant_service.entity.Restaurant;
import com.fooddelivery.restaurant_service.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

	
	private final RestaurantService service;
	
	@PostMapping
	public ResponseEntity<Restaurant> create (@RequestBody RestaurantRequest request, @RequestHeader("X-USER-ID") String id,@RequestHeader("X-ROLE") String role ){
		if(!"OWNER".equalsIgnoreCase(role) && !"ADMIN".equalsIgnoreCase(role) ) {
			throw new RuntimeException("Only can crate restaurant");
		}
		return ResponseEntity.ok(service.create(request, id));
	}
	
	@GetMapping
	public List<Restaurant> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> getRestaurent (@PathVariable long id) {
		
		Restaurant restaurant = service.getById(id);
		
		return ResponseEntity.ok(restaurant);
		
	}
	
	@GetMapping("/search")
	public List<Restaurant> search(@RequestParam String q){
		return service.search(q);
	}
	
	@PatchMapping("/{id}/toggle")
	public Restaurant toggle(@PathVariable Long id,@RequestHeader("X-USER-ID") String userid) {
		return service.toggleStatus(id, userid);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRestaurant(@PathVariable Long id,@RequestHeader("X-ROLE") String role){
		if(!"ADMIN".equalsIgnoreCase(role)) {
			throw new RuntimeException("Only can crate restaurant");
		}
		
		return ResponseEntity.ok(service.delete(id));
	}
	
	@GetMapping("/{restaurantId}/foods")
	public ResponseEntity<List<FoodResponse>> getRestaurantFood(@PathVariable long restaurantId){
		return ResponseEntity.ok(service.getRestaurantFood(restaurantId));
	}

}
