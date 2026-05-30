package com.fooddelivery.delivery_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.delivery_service.dto.DeliveryStatus;
import com.fooddelivery.delivery_service.dto.RiderDto;
import com.fooddelivery.delivery_service.entity.Delivery;
import com.fooddelivery.delivery_service.feignclient.OrderClient;
import com.fooddelivery.delivery_service.feignclient.RiderClient;
import com.fooddelivery.delivery_service.repository.DeliveryRepository;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {
	
	private final DeliveryRepository deliveryRepo;
	
	private final OrderClient orderClient;
	
	private final RiderClient riderClient;
	
	public Delivery assignRider(long orderId) {
		
		List<RiderDto> riders = riderClient.getAvailableRider();

		if (riders.isEmpty()) {
		    throw new RuntimeException("No riders available");
		}
		
		RiderDto rider = riders.get(0);
		
		riderClient.updateStatus(rider.getId(),false);
		
		Delivery delivery = Delivery.builder()
				            .orderId(orderId)
				            .riderId(rider.getId())
				            .status(DeliveryStatus.ASSIGNED)
				            .assigndAt(LocalDateTime.now())
				            .build();
		
		return deliveryRepo.save(delivery);
	}
	
	public Delivery pickupOrder (long orderId) {
		Delivery delivery = deliveryRepo.findByOrderId(orderId).orElseThrow(() -> new NotFoundException("order not found"));
		
		delivery.setPickepedAt(LocalDateTime.now());
		delivery.setStatus(DeliveryStatus.PICKED_UP);
		
		orderClient.updatePaymentStatus(orderId, "OUT_FOR_DELIVERY");
		
		return deliveryRepo.save(delivery);
	}
	
	
	public Delivery markDelivered(long orderId) {
		Delivery delivery = deliveryRepo.findByOrderId(orderId).orElseThrow(() -> new NotFoundException("order not found"));
		
		delivery.setStatus(DeliveryStatus.DELIVERED);
		delivery.setDeliveredAt(LocalDateTime.now());
		
		orderClient.updatePaymentStatus(orderId, "DELIVERED");
		
		riderClient.updateStatus(delivery.getRiderId(), true);
		
		return deliveryRepo.save(delivery);
	}

}
