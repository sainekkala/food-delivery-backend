package com.fooddelivery.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.user_service.dto.AddressRequest;
import com.fooddelivery.user_service.dto.UpdateProfileRequest;
import com.fooddelivery.user_service.dto.UserResponse;
import com.fooddelivery.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
	
	  private final UserService userService;

	    @GetMapping("/me")
	    public UserResponse me(@RequestHeader("X-USER-ID") String userId) {
	        return userService.getProfile(userId);
	    }

	    @PutMapping("/me")
	    public void updateProfile(
	            @RequestHeader("X-USER-ID") String userId,
	            @RequestBody UpdateProfileRequest req) {
	        userService.updateProfile(userId, req);
	    }

	    @PostMapping("/address")
	    public void addAddress(
	            @RequestHeader("X-USER-ID") String userId,
	            @RequestBody AddressRequest req) {
	        userService.addAddress(userId, req);
	    }

}
