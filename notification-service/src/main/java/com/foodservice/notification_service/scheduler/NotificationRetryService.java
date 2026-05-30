package com.foodservice.notification_service.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.foodservice.notification_service.dto.NotificationStatus;
import com.foodservice.notification_service.entity.Notification;
import com.foodservice.notification_service.repository.NotificationRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


@Service
public class NotificationRetryService {
	
	@Autowired
	private NotificationRepository repository;
	
	@Value("${twilio.account.sid}")
	private String accountSid;
	
	@Value("${twilio.auth.token}")
	private String authToken;
	
	@Value("${twilio.phone.number}")
	private String phoneNumber;
	
	@Scheduled(fixedRate = 60000)
	public void retryFailedNotifications() {
		
		List<Notification> faildNotifications = repository.findByStatus(NotificationStatus.FAILED);
		
		for(Notification notification : faildNotifications) {
			
			if(notification.getRetryCount() >= 3) {
				continue;
			}
			
			try {
				
				Twilio.init( accountSid,authToken);
				
				Message.creator(
						 new com.twilio.type.PhoneNumber(notification.getPhoneNumber()),
			                new com.twilio.type.PhoneNumber(phoneNumber),
						notification.getMessage()
						).create();
				
				notification.setStatus(NotificationStatus.SUCCESS);
				
				 System.out.println(
	                        "Retry successful for notification ID: "
	                                + notification.getId());

				
			} catch (Exception e) {
				notification.setRetryCount(notification.getRetryCount()  + 1);
				
				 System.out.println(
	                        "Retry successful for notification ID: "
	                                + notification.getId());

			}
			
			repository.save(notification);
		}
	}

}
