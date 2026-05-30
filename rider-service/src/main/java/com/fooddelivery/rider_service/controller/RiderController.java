package com.fooddelivery.rider_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.rider_service.dto.RiderRequest;
import com.fooddelivery.rider_service.entity.Rider;
import com.fooddelivery.rider_service.service.RiderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/riders")
@RequiredArgsConstructor
public class RiderController {
	
	private final RiderService riderService;
	
	@PostMapping("/register")
	public ResponseEntity<Rider> register(@RequestBody RiderRequest req){
		return ResponseEntity.ok(riderService.registerRider(req));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Rider> login(@RequestParam String phone,@RequestParam String password){
		return ResponseEntity.ok(riderService.login(phone, password));
	}
	
	@PostMapping("/{riderId}/updateLocation")
	public ResponseEntity<Rider> updateLocation(@PathVariable long riderId,@RequestParam double lat,@RequestParam double lon){
		return ResponseEntity.ok(riderService.updateRiderLocation(riderId, lat, lon));
	}
	
	@GetMapping("/rider")
	public ResponseEntity<List<Rider>> getAvailableRider(){
		return ResponseEntity.ok(riderService.findRider());
	}
	
	@PostMapping("/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestParam long riderId,@RequestParam boolean status) {
		riderService.updateStatus(riderId,status);
		return ResponseEntity.ok("status update");
		
	}

}
