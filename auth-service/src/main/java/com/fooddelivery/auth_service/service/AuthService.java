package com.fooddelivery.auth_service.service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.auth_service.dto.AuthResponse;
import com.fooddelivery.auth_service.dto.LoginRequest;
import com.fooddelivery.auth_service.dto.SignupRequest;
import com.fooddelivery.auth_service.entity.RefreshToken;
import com.fooddelivery.auth_service.entity.User;
import com.fooddelivery.auth_service.kafka.UserEventProducer;
import com.fooddelivery.auth_service.repository.RefreshTokenRepository;
import com.fooddelivery.auth_service.repository.UserRepository;
import com.fooddelivery.auth_service.security.JwtService;

@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private UserRepository userrepository;

	@Autowired
	private RefreshTokenRepository refreshtokenrepository;

	@Autowired
	private JwtService jwtservice;

	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Autowired
	private UserEventProducer userEventProducer;

	public AuthService(UserRepository userrepository, RefreshTokenRepository refreshtokenrepository,
			JwtService jwtservice, PasswordEncoder passwordencoder, UserEventProducer userEventProducer) {
		this.userrepository = userrepository;
		this.refreshtokenrepository = refreshtokenrepository;
		this.jwtservice = jwtservice;
		this.passwordencoder = passwordencoder;
		this.userEventProducer = userEventProducer;
	}

	public AuthResponse register(SignupRequest req, long refreshtokenDays) {
		log.info("Register attempt for email: {}", req.getEmail());

		if (userrepository.findByEmail(req.getEmail()).isPresent()) {
			log.warn("Registration failed: user already exists for email {}", req.getEmail());
			throw new IllegalArgumentException("User already exists");
		}

		User u = new User();
		u.setEmail(req.getEmail());
		u.setName(req.getName());
		u.setPasswordHash(passwordencoder.encode(req.getPassword()));

		userrepository.save(u);
		userEventProducer.publishUserCreated(u);
		
		log.info("User registered successfully: {}", u.getEmail());

		String access = jwtservice.generateToken(u.getId(), Map.of("uid", u.getId(), "email", u.getEmail()));
		String refresh = UUID.randomUUID().toString();
		RefreshToken rt = new RefreshToken();

		rt.setUserId(u.getId());
		rt.setToken(refresh);
		rt.setExpiresAt(Instant.now().plusSeconds(refreshtokenDays * 24 * 3600));

		refreshtokenrepository.deleteByUserId(u.getId());
		refreshtokenrepository.save(rt);

		log.info("Access and refresh tokens generated for user: {}", u.getEmail());
		return new AuthResponse(access, refresh,
				jwtservice.parseToken(access).getBody().getExpiration().getTime() - System.currentTimeMillis());
	}

	@Transactional
	public AuthResponse login(LoginRequest req, long refreshtokenDays) {
		log.info("Login attempt for email: {}", req.getEmail());
		try {
			Optional<User> uOpt = userrepository.findByEmail(req.getEmail());
			if (uOpt.isEmpty()) {
				log.warn("Login failed: user not found for email {}", req.getEmail());
				throw new IllegalArgumentException("Invalid credentials");
			}

			User u = uOpt.get();
			if (!passwordencoder.matches(req.getPassword(), u.getPasswordHash())) {
				log.warn("Login failed: invalid password for email {}", req.getEmail());
				throw new IllegalArgumentException("Invalid credentials");
			}

			log.info("Before generating JWT for email: {}", req.getEmail());
			String access = jwtservice.generateToken(u.getId(), Map.of("uid", u.getId(), "email", u.getEmail()));
			log.info("JWT generated successfully for email: {}", req.getEmail());

			String refresh = UUID.randomUUID().toString();
			RefreshToken rt = new RefreshToken();
			rt.setUserId(u.getId());
			rt.setToken(refresh);
			rt.setExpiresAt(Instant.now().plusSeconds(refreshtokenDays * 24 * 3600));

			log.info("Saving refresh token for email: {}", req.getEmail());
			refreshtokenrepository.deleteByUserId(u.getId());
			refreshtokenrepository.save(rt);

			log.info("Login successful for email: {}", req.getEmail());
			return new AuthResponse(access, refresh,
					jwtservice.parseToken(access).getBody().getExpiration().getTime() - System.currentTimeMillis());
		} catch (Exception e) {
			log.error("Login failed due to exception for email {}: {}", req.getEmail(), e.getMessage(), e);
			throw e;
		}
	}

	public AuthResponse refresh(String refreshToken) {
		log.info("Refresh token request received: {}", refreshToken);

		var opt = refreshtokenrepository.findByToken(refreshToken);
		if (opt.isEmpty()) {
			log.warn("Refresh failed: token not found");
			throw new IllegalArgumentException("Invalid refresh token");
		}

		RefreshToken rt = opt.get();
		if (rt.getExpiresAt().isBefore(Instant.now())) {
			refreshtokenrepository.delete(rt);
			log.warn("Refresh failed: token expired for user {}", rt.getUserId());
			throw new IllegalArgumentException("Refresh token expired");
		}

		var uopt = userrepository.findById(rt.getUserId());
		if (uopt.isEmpty()) {
			log.warn("Refresh failed: user not found for id {}", rt.getUserId());
			throw new IllegalArgumentException("User not found");
		}

		User u = uopt.get();
		String access = jwtservice.generateToken(u.getId(), Map.of("uid", u.getId(), "email", u.getEmail()));

		log.info("Refresh token successful for user {}", u.getEmail());
		return new AuthResponse(access, rt.getToken(),
				jwtservice.parseToken(access).getBody().getExpiration().getTime() - System.currentTimeMillis());
	}

	public void logout(String refreshToken) {
		log.info("Logout request for token: {}", refreshToken);
		var opt = refreshtokenrepository.findByToken(refreshToken);
		opt.ifPresent(rt -> {
			refreshtokenrepository.delete(rt);
			log.info("Refresh token deleted for user {}", rt.getUserId());
		});
	}
}
