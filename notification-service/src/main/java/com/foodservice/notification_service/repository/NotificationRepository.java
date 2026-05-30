package com.foodservice.notification_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodservice.notification_service.dto.NotificationStatus;
import com.foodservice.notification_service.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	
	List<Notification> findByStatus(NotificationStatus status);

}
