package com.fooddelivery.delivery_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.delivery_service.entity.Delivery;
import com.fooddelivery.delivery_service.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

	private final DeliveryService deliveryService;
	
	@PostMapping("/assign")
	public ResponseEntity<Delivery> assignRider(@RequestParam long orderId){
		
		return ResponseEntity.ok(deliveryService.assignRider(orderId));
		
		}
	
	@PostMapping("/{orderId}/pickup")
	public ResponseEntity<Delivery> pickupOrder(@PathVariable long orderId){
		return ResponseEntity.ok(deliveryService.pickupOrder(orderId));
	}
	
	@PostMapping("/{orderId}/deliver")
	public ResponseEntity<Delivery> deliver(@PathVariable long orderId){
		return ResponseEntity.ok(deliveryService.markDelivered(orderId));
	}
}
