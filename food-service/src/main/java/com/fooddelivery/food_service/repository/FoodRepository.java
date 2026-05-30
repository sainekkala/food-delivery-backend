package com.fooddelivery.food_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.food_service.entity.FoodItem;

@Repository
public interface FoodRepository extends JpaRepository<FoodItem, Long> {
	
	List<FoodItem> findByRestaurantId (long restaurantId);

}
