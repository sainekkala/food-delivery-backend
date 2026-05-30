package com.foodservice.notification_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.foodservice.notification_service.dto.NotificationStatus;
import com.foodservice.notification_service.dto.NotificationType;
import com.foodservice.notification_service.entity.Notification;
import com.foodservice.notification_service.repository.NotificationRepository;
import com.twilio.Twilio;
//import com.twilio.rest.conversations.v1.conversation.Message;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SmsService {
	
	@Autowired
	private NotificationRepository repository;
	
	@Value("${twilio.account.sid}")
	private String accountSid;
	
	@Value("${twilio.auth.token}")
	private String authToken;
	
	@Value("${twilio.phone.number}")
	private String phoneNumber;
	
	public void sendSMS(String toPhone,String messageText,String userId) {
		
		Notification notification = Notification.builder()
				.userId(userId)
				.message(messageText)
				.phoneNumber(toPhone)
				.type(NotificationType.SMS)
				.createdAt(LocalDateTime.now())
				.build();
		
		try {
			Twilio.init(accountSid, authToken);
			
			 Message.creator(
		                new com.twilio.type.PhoneNumber(toPhone),
		                new com.twilio.type.PhoneNumber(phoneNumber),
		                messageText
		        ).create();
			 
			 notification.setStatus(NotificationStatus.SUCCESS);
			
		} catch (Exception e) {
			// TODO: handle exception
			notification.setStatus(NotificationStatus.FAILED);
			System.out.println("SMS FAILED" + " " + e.getMessage());
		}
		
		repository.save(notification);
	}

}
