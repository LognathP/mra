package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mra.logger.MraLogger;
import com.mra.model.Category;
import com.mra.model.Collections;
import com.mra.model.Customers;
import com.mra.repository.CategoryRepository;
import com.mra.repository.CollectionsRepository;
import com.mra.repository.CustomerRepository;

@Component
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public List<Customers> getAllCustomer() {
		try {
			return (List<Customers>) customerRepo.findAll();
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllCustomer "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<Customers> getCustomer(int id) {
		try {
			return customerRepo.findById((long) id);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getCustomer "+e.getMessage());
		}
		return null;
		
	}

	@Override
	public boolean addCustomer(Customers customer) {
		boolean status = false;
		try {
			customerRepo.save(customer);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addCustomer "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean updateCustomer(Customers customer) {
		boolean status = false;
		try {
			customer.setCreated(customerRepo.findById(customer.getId()).get().getCreated());
			customerRepo.save(customer);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on updateCustomer "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean deleteCustomer(int id){
		boolean status = false;
		try {
			customerRepo.deleteById((long) id);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on deleteCategory "+e.getMessage());
		}
		return status;
	}

	@Override
	public Optional<Customers> getCustomerByPhone(String phoneNo) {
		try {
			return customerRepo.getCustomerByPhone(phoneNo);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getCustomerByPhone "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<Customers> authenticateUser(String email, String password) {
		try {
			return customerRepo.authenticateUser(email, password);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on authenticateUser "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<Customers> getCustomerByEmail(String email) {
		try {
			return customerRepo.getCustomerByEmail(email);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getCustomerByEmail "+e.getMessage());
		}
		return null;
	}
	
	@Override
	public void updatePassword(int customerId, String password) {
		try {
			customerRepo.updatePassword(customerId,password);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on updatePassword "+e.getMessage());
		}
	}

}
