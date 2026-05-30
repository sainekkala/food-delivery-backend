package com.fooddelivery.rider_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.rider_service.entity.Rider;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long>{

	Optional<Rider> findByPhone(String phone);
	
	List<Rider> findByAvailableTrue();
}
