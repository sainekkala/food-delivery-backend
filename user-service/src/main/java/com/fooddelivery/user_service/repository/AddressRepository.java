package com.fooddelivery.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.user_service.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
