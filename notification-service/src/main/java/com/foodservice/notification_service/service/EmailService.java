package com.foodservice.notification_service.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final JavaMailSender mailsender;
	
	public EmailService(JavaMailSender mailsender ) {
		// TODO Auto-generated constructor stub
		this.mailsender = mailsender;
	}
	
	public void sendNotification(String email,Long orderId, String status) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(email);
		message.setSubject("order update");
		message.setText("Dear User,\n\nYour order #" + orderId + " is " + status);
		
		mailsender.send(message);
	}

}
