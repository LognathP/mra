package com.mra.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mra.logger.ConfigProperties;
import com.mra.logger.MraLogger;
import com.mra.model.Address;
import com.mra.model.Address;
import com.mra.model.Collections;
import com.mra.model.Response;
import com.mra.service.AddressService;
import com.mra.service.CollectionService;

@Component
public class AddressBusiness {

	@Autowired
	MraLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	Response response;
	
	public Object getAllAddress(int customerid) {
		List<Address> addressList = addressService.getAllAddress(customerid);
		if(addressList!=null)
		{
			LOGGER.info(getClass(), "ADDRESSESS RETRIEVED SUCCESSFULLY");
			response.setData(addressList);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE ADDRESSESS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object getAddress(int id) {
		LOGGER.debug(getClass(), "ADDRESS ID IN GET "+id);
		Optional<Address> categories = addressService.getAddress(id);
		if(categories.isPresent())
		{
			LOGGER.info(getClass(), "ADDRESS BY ID RETRIEVED SUCCESSFULLY");
			response.setData(categories.get());
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE ADDRESS FOR GIVEN ID");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object addAddress(Address address) {
		if(addressService.addAddress(address))
		{
			LOGGER.info(getClass(), "ADDRESS ADDED SUCCESSFULLY");
			response.setData(address);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO ADD ADDRESS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object updateAddress(Address address) {
		if(addressService.updateAddress(address))
		{
			LOGGER.info(getClass(), "ADDRESS UPDATED SUCCESSFULLY");
			response.setData(address);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO UPDATE ADDRESS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object deleteAddress(int id) {
		LOGGER.debug(getClass(), "ADDRESS ID IN DELETE "+id);
		if(addressService.deleteAddress(id))
		{
			LOGGER.info(getClass(), "ADDRESS DELETED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO DELETE ADDRESS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	
	
	
}
