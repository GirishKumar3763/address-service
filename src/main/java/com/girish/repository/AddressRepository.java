package com.girish.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.girish.model.Address;


public interface AddressRepository extends JpaRepository<Address, Integer> {

	Optional<Address> findByEmployeeId(String empId);

}
