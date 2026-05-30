package com.fooddelivery.payment_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fooddelivery.payment_service.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Optional<Payment> findByOrderId(long orderId);
	
	@Query(""" 
			
			SELECT p from Payment p WHERE p.status = 'RETRY_PENDING' AND p.nextRetryAt <= CURRENT_TIMESTAMP 
""")
	List<Payment> findPaymentsToRetry();
	
	Optional<Payment> findTopByOrderIdOrderByCreatedAtDesc(long orderId);
}
