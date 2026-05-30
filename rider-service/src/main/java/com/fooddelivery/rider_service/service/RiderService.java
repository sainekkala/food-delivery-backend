package com.fooddelivery.rider_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.rider_service.dto.RiderRequest;
import com.fooddelivery.rider_service.entity.Rider;
import com.fooddelivery.rider_service.repository.RiderRepository;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiderService {

	private final RiderRepository riderRepo;
	
	
	public Rider registerRider(RiderRequest req) {
		
		Rider rider = Rider.builder()
				.name(req.getName())
				.phone(req.getPhone())
				.password(req.getPassword())
				.available(true)
				.build();
		
		return riderRepo.save(rider);
	}
	
	public Rider login (String phone,String password) {
		
		Rider rider = riderRepo.findByPhone(phone).orElseThrow(() -> new NotFoundException("rider not found"));
		
		if(!rider.getPassword().equals(password)) {
			throw new RuntimeException("invalid password");
		}
		
		return rider;
	}
	
	public Rider updateRiderLocation(Long riderId, Double lat, Double lon) {
		
		Rider rider = riderRepo.findById(riderId).orElseThrow(() -> new NotFoundException("rider not found"));
		
		rider.setLatitude(lat);
		rider.setLongitude(lon);
		
		return riderRepo.save(rider);
	}
	
	public List<Rider> findRider(){
		return riderRepo.findByAvailableTrue();
	}
	
	
	public void updateStatus(long riderId,boolean status) {
		Rider rider = riderRepo.findById(riderId).orElseThrow(() -> new NotFoundException("rider not found"));
		
		rider.setAvailable(status);
		
		riderRepo.save(rider);
	}
	
		
	
}
