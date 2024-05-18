package com.girish.service;

import java.util.List;
import java.util.Optional;

import com.girish.model.Address;


public interface AddressService {
	
	public List<Address> getAllAddressByAllEmployees();
	public Optional<Address> findAddressById(Integer addressId);	
	public void deleteAddress(Integer addressId);
	public Address addAddress(Address address);
	public Address updateAddress(Address address);
	

}