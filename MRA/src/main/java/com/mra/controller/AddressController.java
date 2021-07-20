package com.mra.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mra.business.AddressBusiness;
import com.mra.business.AddressBusiness;
import com.mra.logger.MraLogger;
import com.mra.model.Address;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AddressController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	MraLogger Logger;
	
	@Autowired
	AddressBusiness addressBusiness;

	@GetMapping("/api/address/getall/{id}")
	public Object getAllAddress(@PathVariable ("id") int customerId) {
		Logger.info(this.getClass(),"GET ALL ADDRESSESS API CALL STARTED AT "+dateFormat.format(new Date()));
		return addressBusiness.getAllAddress(customerId);
	}
	@GetMapping("/api/address/get/{id}")
	public Object getAddress(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"GET ADDRESS BY ID API CALL STARTED AT "+dateFormat.format(new Date()));
		return addressBusiness.getAddress(id);
	}
	@PostMapping("/api/address/add")
	public Object addAddress(@RequestBody Address address) {
		Logger.info(this.getClass(),"ADD ADDRESS API CALL STARTED AT "+dateFormat.format(new Date()));
		return addressBusiness.addAddress(address);
	}
	@PutMapping("/api/address/update")
	public Object updateAddress(@RequestBody Address address) {
		Logger.info(this.getClass(),"UPDATE ADDRESS API CALL STARTED AT "+dateFormat.format(new Date()));
		return addressBusiness.updateAddress(address);
	}
	@DeleteMapping("/api/address/delete/{id}")
	public Object deleteAddress(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"DELETE ADDRESS API CALL STARTED AT "+dateFormat.format(new Date()));
		return addressBusiness.deleteAddress(id);
	}
	
	
}
