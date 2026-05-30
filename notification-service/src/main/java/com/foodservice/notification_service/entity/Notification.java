package com.foodservice.notification_service.entity;

import java.time.LocalDateTime;

import com.foodservice.notification_service.dto.NotificationStatus;
import com.foodservice.notification_service.dto.NotificationType;

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
@Table(name = "notifications")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userId;
	
	private String message;
	
	@Enumerated(EnumType.STRING)
	private NotificationStatus status;
	
	@Enumerated(EnumType.STRING)
	private NotificationType type;
	
	private String phoneNumber;
	
	private String email;
	
	private Integer retryCount = 0;
	
	private LocalDateTime createdAt;
}
