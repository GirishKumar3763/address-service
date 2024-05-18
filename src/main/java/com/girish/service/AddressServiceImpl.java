package com.girish.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.girish.model.Address;
import com.girish.repository.AddressRepository;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	AddressRepository addressRepository;

	@Override
	public List<Address> getAllAddressByAllEmployees() {
		// TODO Auto-generated method stub
		return (List<Address>) addressRepository.findAll();
	}

	@Override
	public Optional<Address> findAddressById(Integer addressId) {
		// TODO Auto-generated method stub
		return addressRepository.findById(addressId);
	}

	@Override
	public void deleteAddress(Integer addressId) {
		// TODO Auto-generated method stub
		addressRepository.deleteById(addressId);
		
	}

	@Override
	public Address addAddress(Address address) {
		// TODO Auto-generated method stub
		return addressRepository.save(address) ;
	}

	@Override
	public Address updateAddress(Address address) {
		// TODO Auto-generated method stub
		return addressRepository.save(address) ;
	}
	
	
	
	
}