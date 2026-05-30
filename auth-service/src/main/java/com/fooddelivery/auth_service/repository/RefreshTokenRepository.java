package com.fooddelivery.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.auth_service.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String>{
	
	Optional<RefreshToken> findByToken(String token);
	
	void deleteByUserId(String Userid);

}
