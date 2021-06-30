package com.mra.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mra.business.ProductBusiness;
import com.mra.logger.MraLogger;
import com.mra.model.Product;
import com.mra.model.ProductRequest;

@RestController
public class ProductController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	MraLogger Logger;
	
	@Autowired
	ProductBusiness productBusiness;

	@GetMapping("/api/product/getall")
	public Object getAllProduct() {
		Logger.info(this.getClass(),"GET ALL PRODUCTS API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.getAllProduct();
	}
	@GetMapping("/api/product/get/{id}")
	public Object getProduct(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"GET PRODUCT BY ID API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.getProduct(id);
	}
	@RequestMapping(value = "/api/product/add",method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public Object addProduct(@ModelAttribute ProductRequest product) throws Exception {
		Logger.info(this.getClass(),"ADD PRODUCT API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.addProduct(product);
	}
	@DeleteMapping("/api/product/delete/{id}")
	public Object deleteProduct(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"DELETE PRODUCT API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.deleteProduct(id);
	}
	@GetMapping("/product/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadProduct(@PathVariable String fileName, HttpServletRequest request) throws Exception {
		Logger.info(this.getClass(),"DOWNLOAD PRODUCT API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.downloadProduct(fileName,request);   
	}
	
	
	
}
