package com.fooddelivery.restaurant_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.restaurant_service.dto.FoodResponse;
import com.fooddelivery.restaurant_service.dto.RestaurantRequest;
import com.fooddelivery.restaurant_service.entity.Restaurant;
import com.fooddelivery.restaurant_service.feignclient.FoodClient;
import com.fooddelivery.restaurant_service.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {
	
	private final RestaurantRepository repository;
	
	private final FoodClient client;
	
	
	public Restaurant create(RestaurantRequest req, String ownerId) {
		return repository.save(
				Restaurant.builder()
				.name(req.getName())
				.description(req.getDescription())
				.cuisine(req.getCuisine())
				.city(req.getCity())
				.openTime(req.getOpenTime())
				.closeTime(req.getCloseTime())
				.isOpen(true)
				.rating(0.0)
				.ownerId(ownerId)
				.build()
				);
	}
	
	public List<Restaurant> getAll(){
		return repository.findAll();
	}
	
	public List<Restaurant> search (String q){
		return repository.search(q);
	}
	
	public Restaurant toggleStatus (long id, String ownerId) {
		Restaurant r = repository.findById(id).orElseThrow(() -> 
			new RuntimeException("Restaurant not found")
		);
		
		if(!r.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("not authorized");
		}
		
		r.setOpen(!r.isOpen());
		
		return repository.save(r);
	}

	public String delete(Long id) {
		 repository.deleteById(id);
		 return "Restaurant deleted successful";
		
	}
	
	 public Restaurant getById(Long id) {
	        return repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
	    }
	
	public List<FoodResponse> getRestaurantFood(Long reataurantId){
		
		getById(reataurantId);
		
		return client.getFoodsByRestaurant(reataurantId);
	}
	

}
