package com.foodservice.notification_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.foodservice.notification_service.dto.OrderEvent;
import com.foodservice.notification_service.service.EmailService;
import com.foodservice.notification_service.service.SmsService;
import com.google.api.client.util.Value;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {
	
	
	
	private final EmailService emailservice;
	private final SmsService smsService;

    @KafkaListener(topics = "order-events", groupId = "notification-group-v3")
    public void consumeOrderEvent(OrderEvent event) {
        log.info("================== ORDER EVENT RECEIVED ==================");
        log.info("Order ID: {}", event.getOrderId());
        log.info("Order Status: {}", event.getStatus());
        log.info("User ID: {}", event.getUserId());
        log.info("==========================================================");
        
        // Here you can implement your notification logic
        // For example: send email, push notification, SMS, etc.
        String email = "test@gmail.com";
        String phone = "+918341638569";

        String message = "Your order #" + event.getOrderId() + " created";
                         
        
        emailservice.sendNotification(email, event.getOrderId(), event.getStatus());
        smsService.sendSMS(phone,message,event.getUserId());
    }
    
//    private void sendNotification(OrderEvent event) {
//        // Example notification logic
//        String message = String.format(
//            "Dear User, your order #%d has been %s", 
//            event.getOrderId(), 
//            event.getStatus()
//        );
//        
//        log.info("📧 SENDING NOTIFICATION: {}", message);
//        
//        // You can extend this to:
//        // - Save to database
//        // - Send email via JavaMailSender
//        // - Send SMS via Twilio
//        // - Send push notification via Firebase
//        // - Call notification API
//    }
}
