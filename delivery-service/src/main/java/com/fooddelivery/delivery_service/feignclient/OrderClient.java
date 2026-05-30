package com.fooddelivery.delivery_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service", url = "http://localhost:8085")
public interface OrderClient {
	
	@PutMapping("api/orders/{orderId}/payment-status")
	public void updatePaymentStatus(@PathVariable long orderId, @RequestParam String status);

}
