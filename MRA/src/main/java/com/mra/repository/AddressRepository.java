package com.mra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mra.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
