package com.fooddelivery.order_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.order_service.dto.CreateOrderRequest;
import com.fooddelivery.order_service.dto.OrderStatus;
import com.fooddelivery.order_service.entity.Order;
import com.fooddelivery.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService service;
	
	@PostMapping
	public ResponseEntity<Order> create(@RequestHeader("X-USER-ID") String userid, @RequestBody CreateOrderRequest req){
		return ResponseEntity.ok(service.create(userid, req));
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>> getUserOrders(@RequestHeader("X-USER-ID") String userid){
		return ResponseEntity.ok(service.getUseOrders(userid));
		
	}
	
	@PutMapping("/{orderId}/payment-status")
	public void updatePaymentStatus(@PathVariable long orderId, @RequestParam OrderStatus status) {
		service.updateStatus(orderId,status);
	}
	
	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<String> cancelOrder(@PathVariable long orderId) {
		
		service.cancelOrder(orderId);
		
		return ResponseEntity.ok("order cancelled");
	}

}
