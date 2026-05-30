package com.fooddelivery.user_service.service;



import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.user_service.dto.AddressRequest;
import com.fooddelivery.user_service.dto.AddressResponse;
import com.fooddelivery.user_service.dto.UpdateProfileRequest;
import com.fooddelivery.user_service.dto.UserResponse;
import com.fooddelivery.user_service.entity.Address;
import com.fooddelivery.user_service.entity.User;
import com.fooddelivery.user_service.repository.AddressRepository;
import com.fooddelivery.user_service.repository.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	private final AddressRepository addressRepository;
	
	public UserResponse getProfile(String userId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("user not found"));

	    return toUserResponse(user);
	}
	
	public void updateProfile (String userId, UpdateProfileRequest req) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
		
		user.setName(req.getName());
		userRepository.save(user);
	}

	@Transactional
	public void addAddress(String userId, AddressRequest req) {

	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User profile not created yet"));

	    Address address = Address.builder()
	            .street(req.getStreet())
	            .city(req.getCity())
	            .state(req.getState())
	            .pincode(req.getPincode())
	            .build();

	    address.setUser(user);
	    user.getAddress().add(address);

	    userRepository.save(user); // cascade saves address
	}
	
	public UserResponse toUserResponse(User user) {
	    UserResponse dto = new UserResponse();
	    dto.setId(user.getId());
	    dto.setName(user.getName());
	    dto.setEmail(user.getEmail());
	    dto.setRole(user.getRole());

	    dto.setAddresses(
	        user.getAddress().stream()
	            .map(addr -> {
	                AddressResponse a = new AddressResponse();
	                a.setId(addr.getId());
	                a.setStreet(addr.getStreet());
	                a.setCity(addr.getCity());
	                a.setState(addr.getState());
	                a.setPincode(addr.getPincode());
	                return a;
	            })
	            .collect(Collectors.toList())
	    );

	    return dto;
	}


}
