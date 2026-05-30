package com.fooddelivery.delivery_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.delivery_service.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

	Optional<Delivery> findByOrderId(long orderId);
}
