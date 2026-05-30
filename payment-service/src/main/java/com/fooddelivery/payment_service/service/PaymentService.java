package com.fooddelivery.payment_service.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fooddelivery.payment_service.dto.CreatePaymentRequest;
import com.fooddelivery.payment_service.dto.FakePaymentGateway;
import com.fooddelivery.payment_service.dto.PaymentResponse;
import com.fooddelivery.payment_service.dto.PaymentStatus;
import com.fooddelivery.payment_service.entity.Payment;
import com.fooddelivery.payment_service.feignclient.OrderClient;
import com.fooddelivery.payment_service.repository.PaymentRepository;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	
	private final PaymentRepository repo;
	private final OrderClient orderclient;
	private final FakePaymentGateway gateway;
	
	
	
	public PaymentResponse create(String userId,CreatePaymentRequest req) {
		
		Payment payment = Payment.builder()
				          .orderId(req.getOrderId())
				          .userId(userId)
				          .amount(req.getAmount())
				          .status(PaymentStatus.CREATED)
				          .createdAt(LocalDateTime.now())
				          .build();
		
		repo.save(payment);
		
		processPayment(payment);
		
		return new PaymentResponse(payment.getId(), payment.getStatus().name());
	}
	
	private void processPayment(Payment payment) {
		payment.setStatus(PaymentStatus.PROCESSING);
		repo.save(payment);
		
		boolean sucess = gateway.charge(payment.getAmount());
		
		
		if(sucess) {
			payment.setStatus(PaymentStatus.SUCCESS);
			
			repo.save(payment);
			
			orderclient.updatePaymentStatus(payment.getOrderId(), "PAID");
		}else {
			payment.setStatus(PaymentStatus.RETRY_PENDING);
			
			payment.setRetryCount(1);
			
			payment.setNextRetryAt(LocalDateTime.now().plusSeconds(30));
			
			repo.save(payment);
			
			
		}
	}
}
