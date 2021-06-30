package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mra.model.Category;
import com.mra.model.Customers;

@Service
public interface CustomerService {

	List<Customers> getAllCustomer();

	Optional<Customers> getCustomer(int id);

	boolean addCustomer(Customers customer);

	boolean deleteCustomer(int id);

	boolean updateCustomer(Customers customer);
	
	Optional<Customers> getCustomerByPhone(String phoneNo);

	Optional<Customers> authenticateUser(String email, String password);

	Optional<Customers> getCustomerByEmail(String email);

	void updatePassword(int customerId, String password);

}
