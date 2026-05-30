package com.fooddelivery.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.payment_service.entity.Refund;
@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

}
