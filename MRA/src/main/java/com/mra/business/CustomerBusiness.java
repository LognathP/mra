package com.mra.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mra.logger.ConfigProperties;
import com.mra.logger.MraLogger;
import com.mra.model.Customers;
import com.mra.model.Collections;
import com.mra.model.Response;
import com.mra.service.CustomerService;
import com.mra.service.OtpService;
import com.mra.utils.DESEncryptor;
import com.mra.service.CollectionService;
import com.mra.service.CustomerService;

@Component
public class CustomerBusiness {

	@Autowired
	MraLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	Response response;
	
	@Autowired
	OtpService otpService;
	
	public Object getAllCustomer() {
		List<Customers> customerList = customerService.getAllCustomer();
		if(customerList!=null)
		{
			LOGGER.info(getClass(), "CUSTOMERS RETRIEVED SUCCESSFULLY");
			response.setData(customerList);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE CUSTOMERS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData("Unable to Fetch Customers List");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object getCustomer(int id) {
		LOGGER.debug(getClass(), "CUSTOMER ID IN GET "+id);
		Optional<Customers> categories = customerService.getCustomer(id);
		if(categories.isPresent())
		{
			LOGGER.info(getClass(), "CUSTOMER BY ID RETRIEVED SUCCESSFULLY");
			response.setData(categories.get());
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE CUSTOMER FOR GIVEN ID");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData("Unable to Fetch Customer Details");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object addCustomer(Customers customer) {
		return checkCustomerAndAdd(customer);
	}

	public Object updateCustomer(Customers customer) {
		if(customerService.updateCustomer(customer))
		{
			LOGGER.info(getClass(), "CUSTOMER UPDATED SUCCESSFULLY");
			response.setData(customer);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO UPDATE CUSTOMER");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData("Unable to Add/Update Customer");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object deleteCustomer(int id) {
		LOGGER.debug(getClass(), "CUSTOMER ID IN DELETE "+id);
		if(customerService.deleteCustomer(id))
		{
			LOGGER.info(getClass(), "CUSTOMER DELETED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			
			LOGGER.error(getClass(), "UNABLE TO DELETE CUSTOMER");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData("Unable to Delete Customer");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	public Object checkCustomerAndAdd(Customers customer)
	{
		if(customerService.getCustomerByEmail(customer.getEmail()).isPresent())
		{
			LOGGER.info(getClass(), "EMAIL ID ALREADY EXISTS");
			response.setData("Email ID Already Exists");
			response.setStatus(HttpStatus.FORBIDDEN);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else if(customerService.getCustomerByPhone(customer.getMobile_no()).isPresent())
		{
			LOGGER.info(getClass(), "MOBILE NO ALREADY EXISTS");
			response.setData("Mobile Number Already Exists");
			response.setStatus(HttpStatus.FORBIDDEN);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			customer.setPassword(DESEncryptor.encrypt(customer.getPassword(), configProp.getConfigValue("password.key")));
			if(customerService.addCustomer(customer))
			{
				LOGGER.info(getClass(), "CUSTOMER ADDED SUCCESSFULLY");
				response.setData(customer);
				response.setStatus(HttpStatus.OK);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				LOGGER.error(getClass(), "UNABLE TO ADD CUSTOMER");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				response.setData("Unable to Add/Update Customer");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		
	}

	public Object loginCustomer(String email, String password) {
		password = DESEncryptor.encrypt(password, configProp.getConfigValue("password.key"));
		if(!customerService.getCustomerByEmail(email).isPresent())
		{
			LOGGER.info(getClass(), "CUSTOMER ADDED SUCCESSFULLY");
			response.setData("Email Id is not Registered");
			response.setStatus(HttpStatus.FORBIDDEN);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			Optional<Customers> customer = customerService.authenticateUser(email, password);
			if(customer.isPresent())
			{
				LOGGER.info(getClass(), "LOGGED IN SUCCESSFULLY");
				response.setData(customer.get());
				response.setStatus(HttpStatus.OK);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				LOGGER.error(getClass(), "USERNAME OR PASSWORD INCORRECT");
				response.setStatus(HttpStatus.FORBIDDEN);
				response.setData("Username or Password Incorrect");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		
	}

	public Object forgotPassword(String mobileNo, String password) {
		Optional<Customers> customer = customerService.getCustomerByPhone(mobileNo);
		if(customer.isPresent())
		{
			int customerId = customer.get().getId().intValue();
			password = DESEncryptor.encrypt(password, configProp.getConfigValue("password.key"));
			customerService.updatePassword(customerId,password);
			LOGGER.info(getClass(), "CUSTOMER PASSWORD UPDATED SUCCESSFULLY "+customerId);
			response.setData("Password Updated Successfully");
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "CUSTOMER ID IS NOT PRESENT "+mobileNo);
			response.setStatus(HttpStatus.FORBIDDEN);
			response.setData("Customer Id id not availabe");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object resetPassword(int customerId, String currentPassword, String newPassword) {
		Optional<Customers> customer = customerService.getCustomer(customerId);
		if(currentPassword.equals(newPassword))
		{
			LOGGER.error(getClass(), "CURRENT & NEW PASSWORD SAME FOR"+customerId);
			response.setStatus(HttpStatus.FORBIDDEN);
			response.setData("Current Password & New Password Cannot be Same");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else if(customer.isPresent())
		{
			newPassword = DESEncryptor.encrypt(newPassword, configProp.getConfigValue("password.key"));
			customerService.updatePassword(customerId,newPassword);
			LOGGER.info(getClass(), "CUSTOMER PASSWORD UPDATED SUCCESSFULLY FOR "+customerId);
			response.setData("Password Updated Successfully");
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "CUSTOMER ID IS NOT PRESENT "+customerId);
			response.setStatus(HttpStatus.FORBIDDEN);
			response.setData("Customer Id id not availabe");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object sendMobileOtp(String mobileNo) {
		Optional<Customers> customer = customerService.getCustomerByPhone(mobileNo);
		if(!customer.isPresent())
		{
			LOGGER.error(getClass(), "MOBILE NUMBER NOT EXISTS "+mobileNo);
			response.setData("Mobile Number Not Registered with Us");
			response.setStatus(HttpStatus.FORBIDDEN);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			if(otpService.sendOtp(mobileNo, otpService.generateOTP(mobileNo)).equals(HttpStatus.OK))
			{
				LOGGER.info(getClass(), "OTP SENT SUCCESSFULLY "+mobileNo);
				response.setData("OTP Sent to Your mobile Number");
				response.setStatus(HttpStatus.OK);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				LOGGER.error(getClass(), "UNABLE TO SEND OTP "+mobileNo);
				response.setData("Unable to Send OTP to Mobile Number");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			
			
		}
	}

	public Object verifyOtp(String mobileNo, int otp) {
		Optional<Customers> customer = customerService.getCustomerByPhone(mobileNo);
		if(!customer.isPresent())
		{
			LOGGER.error(getClass(), "MOBILE NUMBER NOT EXISTS " + mobileNo);
			response.setData("Mobile Number Not Registered with Us");
			response.setStatus(HttpStatus.FORBIDDEN);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			if(otpService.getOtp(mobileNo) == otp)
			{
				otpService.clearOTP(mobileNo);
				LOGGER.info(getClass(), "OTP VERIFIED SUCCESSFULLY "+mobileNo);
				response.setData("OTP Verified Successfully");
				response.setStatus(HttpStatus.OK);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				LOGGER.error(getClass(), "OTP VERIFICATION FAILED "+mobileNo);
				response.setData("OTP Verification Failed for Mobile Number");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			
			
		}
	}

	
	
	
	
}
