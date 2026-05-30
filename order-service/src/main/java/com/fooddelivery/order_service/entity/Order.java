package com.fooddelivery.order_service.entity;

import java.time.LocalDateTime;

import com.fooddelivery.order_service.dto.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String userId;
	
	private long restaurantId;
	
	private double totalAmount;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus Status;
	
	private LocalDateTime createdAt;

}
