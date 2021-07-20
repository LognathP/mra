package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mra.model.Address;
import com.mra.model.Category;

@Service
public interface AddressService {

	List<Address> getAllAddress(int customerid);

	Optional<Address> getAddress(int id);

	boolean addAddress(Address address);

	boolean updateAddress(Address address);

	boolean deleteAddress(int id);

}
