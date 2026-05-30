package com.fooddelivery.auth_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.auth_service.entity.User;
import com.fooddelivery.auth_service.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userrepository;
	
	public User save(User u) {
		
		return userrepository.save(null);
		
	}
	
	public Optional<User> findByEmail(String mail){
		
		return userrepository.findByEmail(mail);
		
	}
	
	public Optional<User> findById(String id){
		
		return userrepository.findById(id);
		
	}

}
