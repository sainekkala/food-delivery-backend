package com.fooddelivery.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {

	
	private String id;
	
	private String email;
	
	private String name;
	
	private String role;
}
