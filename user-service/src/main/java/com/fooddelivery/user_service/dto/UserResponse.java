package com.fooddelivery.user_service.dto;

import java.util.List;

import com.fooddelivery.user_service.dto.AddressResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	
	private String id;
	
	private String name;
	
	private String email;
	
	private String role;
	
	private List<AddressResponse> addresses;

}
