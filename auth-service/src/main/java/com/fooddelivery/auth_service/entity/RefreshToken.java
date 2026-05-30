package com.fooddelivery.auth_service.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RefreshToken {
	
	@Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiresAt;

}
