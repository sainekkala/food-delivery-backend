package com.fooddelivery.restaurant_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.restaurant_service.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	List<Restaurant> findByCityIgnoreCase(String city);
	
	List<Restaurant> findByCuisineIgnoreCase(String cuisine);
	
	 @Query("""
		        SELECT r FROM Restaurant r
		        WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :q, '%'))
		           OR LOWER(r.description) LIKE LOWER(CONCAT('%', :q, '%'))
		    """)
	List<Restaurant> search(@Param("q") String q);
	

}
