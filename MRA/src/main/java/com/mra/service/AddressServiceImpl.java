package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mra.dao.AddressDao;
import com.mra.logger.MraLogger;
import com.mra.model.Address;
import com.mra.repository.AddressRepository;

@Component
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepo;
	
	@Autowired
	AddressDao addressDao;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public List<Address> getAllAddress(int customerid) {
		try {
			return addressDao.getAddressByCustomer(customerid);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllAddress "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<Address> getAddress(int id) {
		try {
			return addressRepo.findById((long) id);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAddress "+e.getMessage());
		}
		return null;
		
	}

	@Override
	public boolean addAddress(Address address) {
		boolean status = false;
		try {
			addressRepo.save(address);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addAddress "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean updateAddress(Address address) {
		boolean status = false;
		try {
			address.setCreated(addressRepo.findById(address.getId()).get().getCreated());
			addressRepo.save(address);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addAddress "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean deleteAddress(int id) {
		boolean status = false;
		try {
			addressRepo.deleteById((long) id);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on deleteAddress "+e.getMessage());
		}
		return status;
	}

}
