package com.fooddelivery.delivery_service.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fooddelivery.delivery_service.dto.RiderDto;


@FeignClient(name = "rider-service", url = "http://localhost:8089")
public interface RiderClient {
	
	@GetMapping("/api/riders/rider")
	public List<RiderDto> getAvailableRider();
	
	@PostMapping("/api/riders/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestParam long riderId,@RequestParam boolean status);

}
