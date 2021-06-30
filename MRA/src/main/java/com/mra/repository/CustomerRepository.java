package com.mra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mra.model.Category;
import com.mra.model.Customers;

public interface CustomerRepository extends CrudRepository<Customers, Long>{
	
	@Query("SELECT c FROM Customers c where c.mobile_no = :no")
	Optional<Customers> getCustomerByPhone(@Param("no") String number);
		
	@Query("SELECT c FROM Customers c where c.email = :email and c.password = :pass")
	Optional<Customers> authenticateUser(@Param("email") String email,@Param("pass") String password);
	
	@Query("SELECT c FROM Customers c where c.email = :email")
	Optional<Customers> getCustomerByEmail(@Param("email") String email);

	@Query("UPDATE Customers c SET c.password =:password where c.id = :customerId")
	void updatePassword(@Param("customerId") int customerid, @Param("password") String password);

}
