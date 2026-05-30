package com.fooddelivery.payment_service.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fooddelivery.payment_service.dto.FakePaymentGateway;
import com.fooddelivery.payment_service.dto.PaymentStatus;
import com.fooddelivery.payment_service.entity.Payment;
import com.fooddelivery.payment_service.feignclient.OrderClient;
import com.fooddelivery.payment_service.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentRetryScheduler {

	private final PaymentRepository repo;
	
	private final FakePaymentGateway gateway;
	
	private final OrderClient orderClient;
	
	private static final int MAX_RETRY = 3;
	
	@Scheduled(fixedDelay = 300000)
	public void retryFaildPayments() {
		
		List<Payment> failedPayments = repo.findPaymentsToRetry();
		
		for(Payment payment : failedPayments) {
			
			log.info("Retry payments",payment.getId());
			
			boolean sucess = gateway.charge(payment.getAmount());
			
			if(sucess) {
				payment.setStatus(PaymentStatus.SUCCESS);
				repo.save(payment);
				orderClient.updatePaymentStatus(payment.getOrderId(), "PAID");
			}else {
				payment.setRetryCount(payment.getRetryCount() + 1);
				
				if(payment.getRetryCount() >= MAX_RETRY) {
					payment.setStatus(PaymentStatus.FAILED);
					orderClient.updatePaymentStatus(payment.getOrderId(), "PAYMENT_FAILED");
				}else {
					payment.setNextRetryAt(LocalDateTime.now().plusSeconds(30));
				}
				
				repo.save(payment);
			}
		}
		
	}
}
