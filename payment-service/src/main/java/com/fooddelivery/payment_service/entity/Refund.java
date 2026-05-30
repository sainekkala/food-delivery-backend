package com.fooddelivery.payment_service.entity;

import java.time.LocalDateTime;

import com.fooddelivery.payment_service.dto.RefundStatus;

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
@Data
@Table(name = "refunds")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long paymentId;
	
	private long orderId;
	
	private double amount;
	
	@Enumerated(EnumType.STRING)
	private RefundStatus status;
	
	private LocalDateTime createdAt;
}
