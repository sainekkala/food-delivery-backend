package com.fooddelivery.delivery_service.entity;

import java.time.LocalDateTime;

import com.fooddelivery.delivery_service.dto.DeliveryStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	private Long orderId;
	
	private Long riderId;
	
	private DeliveryStatus status;
	
	private LocalDateTime assigndAt;
	
	private LocalDateTime pickepedAt;
	
	private LocalDateTime deliveredAt;

}
