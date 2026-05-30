package com.fooddelivery.food_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.food_service.dto.CreateFoodRequest;
import com.fooddelivery.food_service.entity.FoodItem;
import com.fooddelivery.food_service.repository.FoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {
	
	private final FoodRepository foodRepository;
	
	public FoodItem create(CreateFoodRequest req) {
		FoodItem item = FoodItem.builder()
				.name(req.getName())
				.description(req.getDescription())
				.price(req.getPrice())
				.category(req.getCategory())
				.restaurantId(req.getRestaurantId())
				.build();
		return foodRepository.save(item);
	}
	
	public List<FoodItem> getByRestaurant(long restaurantId){
		
		return foodRepository.findByRestaurantId(restaurantId);
		
	}
	
	public String delete(long id) {
		foodRepository.deleteById(id);
		return "Food item deleted successfully";
	}

}
