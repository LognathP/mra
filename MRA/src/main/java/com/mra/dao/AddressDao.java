package com.mra.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mra.model.Address;

@Component
public interface AddressDao {

	List<Address> getAddressByCustomer(int customerId);
}
