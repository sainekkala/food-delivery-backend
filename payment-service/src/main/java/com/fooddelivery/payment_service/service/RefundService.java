package com.fooddelivery.payment_service.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fooddelivery.payment_service.dto.FakeRefund;
import com.fooddelivery.payment_service.dto.PaymentStatus;
import com.fooddelivery.payment_service.dto.RefundStatus;
import com.fooddelivery.payment_service.entity.Payment;
import com.fooddelivery.payment_service.entity.Refund;
import com.fooddelivery.payment_service.feignclient.OrderClient;
import com.fooddelivery.payment_service.repository.PaymentRepository;
import com.fooddelivery.payment_service.repository.RefundRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundService {
	
	private final RefundRepository refundRepo;
	
	private final PaymentRepository paymentRepo;
	
	private final FakeRefund fakeRefund;
	
	private final OrderClient orderClient;
	
	public void initiateRefund(long orderId) {
		
		Payment payment = paymentRepo.findTopByOrderIdOrderByCreatedAtDesc(orderId).orElseThrow(() -> new RuntimeException("payment not found"));
		
		if(!payment.getStatus().equals(PaymentStatus.SUCCESS)) {
			throw new RuntimeException("Can not refund unpaid order");
		}
		
		Refund refund = Refund.builder()
				.orderId(orderId)
				.paymentId(payment.getId())
				.amount(payment.getAmount())
				.status(RefundStatus.INITIATED)
				.createdAt(LocalDateTime.now())
				.build();
		
		refundRepo.save(refund);
		orderClient.updatePaymentStatus(orderId, "REFUND_INITIATED");
		processRefund(refund);
	}
	
	public void processRefund(Refund refund) {
		refund.setStatus(RefundStatus.PROCESSING);
		refundRepo.save(refund);
		
		boolean sucess = fakeRefund.refund(refund.getAmount());
		
		if(sucess) {
			refund.setStatus(RefundStatus.COMPLETED);
			orderClient.updatePaymentStatus(refund.getOrderId(), "REFUNDED");
		}else {
			refund.setStatus(RefundStatus.FAILED);
			orderClient.updatePaymentStatus(refund.getOrderId(), "REFUND_FAILED");
		}
		
		refundRepo.save(refund);
	}

}
