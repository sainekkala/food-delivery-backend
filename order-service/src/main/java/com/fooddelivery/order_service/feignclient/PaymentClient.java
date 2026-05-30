package com.fooddelivery.order_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-client",url = "http://localhost:8086")
public interface PaymentClient {

	@PostMapping("api/internal/refunds/{orderId}")
	public void refund(@PathVariable long orderId);
	
}
