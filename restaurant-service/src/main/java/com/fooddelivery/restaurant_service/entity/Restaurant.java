package com.fooddelivery.restaurant_service.entity;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String description;
	
	private String cuisine;
	
	private String city;
	
	private boolean isOpen;
	
	private double rating;
	
	private LocalTime openTime;
	
	private LocalTime closeTime;
	
	private String ownerId;
	
	

}
