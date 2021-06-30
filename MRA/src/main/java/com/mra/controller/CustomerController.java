package com.mra.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mra.business.CustomerBusiness;
import com.mra.business.CustomerBusiness;
import com.mra.logger.MraLogger;
import com.mra.model.Customers;

@RestController
public class CustomerController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	MraLogger Logger;
	
	@Autowired
	CustomerBusiness customerBusiness;

	@GetMapping("/api/customer/getall")
	public Object getAllCustomer() {
		Logger.info(this.getClass(),"GET ALL CUSTOMERS API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.getAllCustomer();
	}
	@GetMapping("/api/customer/get/{id}")
	public Object getCustomer(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"GET CUSTOMER BY ID API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.getCustomer(id);
	}
	@PostMapping("/api/customer/add")
	public Object addCustomer(@RequestBody Customers customer) {
		Logger.info(this.getClass(),"ADD CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.addCustomer(customer);
	}
	@PutMapping("/api/customer/update")
	public Object updateCustomer(@RequestBody Customers customer) {
		Logger.info(this.getClass(),"UPDATE CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.updateCustomer(customer);
	}
	@DeleteMapping("/api/customer/delete/{id}")
	public Object deleteCustomer(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"DELETE CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.deleteCustomer(id);
	}
	@PostMapping("/api/customer/login")
	public Object loginCustomer(@RequestParam String email,@RequestParam String password) {
		Logger.info(this.getClass(),"LOGIN CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.loginCustomer(email,password);
	}
	@PostMapping("/api/customer/forgotpassword")
	public Object forgotPassword(@RequestParam String mobileNo,@RequestParam String password) {
		Logger.info(this.getClass(),"FORGOT PASSWORD UPDATE API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.forgotPassword(mobileNo,password);
	}
	@PostMapping("/api/customer/resetpassword")
	public Object resetPassword(@RequestParam int customerId,@RequestParam String currentPassword,@RequestParam String newPassword) {
		Logger.info(this.getClass(),"RESET PASSWORD API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.resetPassword(customerId,currentPassword,newPassword);
	}
	@PostMapping("/api/customer/mobileotp")
	public Object sendMobileOtp(@RequestParam String mobileNo) {
		Logger.info(this.getClass(),"SEND MOBILE OTP API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.sendMobileOtp(mobileNo);
	}
	@PostMapping("/api/customer/verifyotp")
	public Object verifyOtp(@RequestParam String mobileNo,@RequestParam int otp) {
		Logger.info(this.getClass(),"VERIFY MOBILE OTP API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.verifyOtp(mobileNo,otp);
	}
	
	
	
}
