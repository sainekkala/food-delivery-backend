package com.fooddelivery.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.auth_service.dto.LoginRequest;
import com.fooddelivery.auth_service.dto.RefreshRequest;
import com.fooddelivery.auth_service.dto.SignupRequest;
import com.fooddelivery.auth_service.service.AuthService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Value("${jwt.refresh-token-days:30}")
	private long refreshTokenDays;
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody SignupRequest req) {
		try {

			var resp = authService.register(req, refreshTokenDays);

			return ResponseEntity.ok(resp);

		} catch (IllegalArgumentException e) {
			// TODO: handle exception

			return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
		}

	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req){
		 try {
	            log.info("Login request for email: {}", req.getEmail());
	            var resp = authService.login(req, refreshTokenDays);
	            log.info("Login response: {}", resp);
	            return ResponseEntity.ok(resp);
	        } catch (IllegalArgumentException e) {
	            log.warn("Login failed: {}", e.getMessage());
	            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
	        }
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody RefreshRequest req){
		try {
			
			var resp = authService.refresh(req.getRefreshToken());
			
			return ResponseEntity.ok(resp);
			
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout (@RequestBody RefreshRequest req){
		authService.logout(req.getRefreshToken());
		
		return ResponseEntity.ok(java.util.Map.of("message", "Logged out"));
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> me (@RequestHeader(name = "Authorization", required = false) String authHeader){
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(401).body(java.util.Map.of("error","Unauthorized"));
		}
		
		try {
			
			String token = authHeader.substring(7);
			var parsed = authService.refresh(token);
			
			return ResponseEntity.ok(java.util.Map.of("message", "token valid"));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(401).body(java.util.Map.of("error", "Invalid token"));
		}
	}
}
