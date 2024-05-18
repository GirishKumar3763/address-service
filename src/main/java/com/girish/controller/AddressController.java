package com.girish.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.girish.Exception.AddressNotFoundException;
import com.girish.model.Address;
import com.girish.service.AddressService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@CrossOrigin
@RestController
public class AddressController {
private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	AddressService addressService;
	
	@GetMapping("/addressbyid/{id}")	
	@RateLimiter(name = "getAddressRateLimit" ,fallbackMethod ="getRateLimitFallBack" )
	public ResponseEntity<Address> getByAddressById(@PathVariable("id") Integer id) {
		logger.info("AddressController - getByAddressId-id:"+id);	
		Address address = addressService.findAddressById(id).orElseThrow(() -> new AddressNotFoundException("Address-"+id+" not found with the given ID."));
		return new ResponseEntity<>(address, HttpStatus.OK);
	}
	
	public ResponseEntity<String> getRateLimitFallBack(RequestNotPermitted exception) {
        logger.info("Rate limit has applied, So no further calls are getting accepted");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
        .body("Too many requests : No further request will be accepted. Please try after sometime");
    }
	
	@GetMapping("/getalladdress")	
	@Retry(name = "getRetryAddressList",fallbackMethod = "getRetryAddressFallBack")
	public ResponseEntity<List<Address>> getAllEmployees() {
		List<Address> address = addressService.getAllAddressByAllEmployees();		
		if (address.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }
		return new ResponseEntity<>(address, HttpStatus.OK);
	}
	public ResponseEntity<String> getRetryAddressFallBack(Exception e) {
		logger.info("--RESPONSE FROM FALLBACK METHOD---");
		
		return new ResponseEntity<String>("SERVER IS DOWN, PLEASE TRT AFTER SOME TIME", null);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteAddress(@PathVariable("id") Integer id) {
		logger.info("AddressController - deleteAddress-id:"+id);	
		Address address =addressService.findAddressById(id).orElseThrow(() -> new AddressNotFoundException("Address-"+id+" not found with the given ID."));	
		addressService.deleteAddress(address.getAddressID());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	@PostMapping("/insertaddress")	
	@CircuitBreaker(name = "saveAddressCircuitBreaker",fallbackMethod = "saveAddressFallback")
	public ResponseEntity<Address> insertAddress(@RequestBody Address address) {
		Address addressObj=addressService.addAddress(address);
		return new ResponseEntity<>(addressObj, HttpStatus.CREATED);
		
	}
	public ResponseEntity<String> saveAddressFallback(Exception e) {
		logger.info("--RESPONSE FROM FALLBACK METHOD---");
		
		return new ResponseEntity<String>("SERVER IS DOWN, PLEASE TRT AFTER SOME TIME", null);
	}

	@PutMapping("/updateaddress")
	//@TimeLimiter(name = "updateAddressTimeLimitter", fallbackMethod = "updateAddressFallback")
	public ResponseEntity<Address>  updateAddress(@RequestBody Address address) {	
		logger.info("AddressController - updateEmployee-id:"+address.getAddressID());	
		addressService.findAddressById(address.getAddressID()).orElseThrow(() -> new AddressNotFoundException("Address-"+address.getEmployeeId()+" not found with the given ID."));	
		return new ResponseEntity<>(addressService.addAddress(address), HttpStatus.OK);	
	}
	/*
	 * public ResponseEntity<String> updateAddressFallback(Exception e) {
	 * logger.info("--RESPONSE FROM FALLBACK METHOD---");
	 * 
	 * return new
	 * ResponseEntity<String>("SERVER IS DOWN, PLEASE TRT AFTER SOME TIME", null); }
	 */
	

}
