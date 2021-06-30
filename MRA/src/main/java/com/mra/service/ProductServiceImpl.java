package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mra.logger.MraLogger;
import com.mra.model.Category;
import com.mra.model.Collections;
import com.mra.model.Patterns;
import com.mra.model.Product;
import com.mra.repository.CategoryRepository;
import com.mra.repository.CollectionsRepository;
import com.mra.repository.PatternRepository;
import com.mra.repository.ProductRepository;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public List<Product> getAllProduct() {
		try {
			return (List<Product>) productRepo.findAll();
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllProduct "+e.getMessage());
		}
		return null;
	}
	@Override
	public Optional<Product> getProduct(int id) {
		try {
			return productRepo.findById((long) id);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getProduct "+e.getMessage());
		}
		return null;
	}
	@Override
	public boolean addProduct(Product product) {
		boolean status = false;
		try {
			productRepo.save(product);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addProduct "+e.getMessage());
		}
		return status;
	}
	@Override
	public boolean deleteProduct(int id) {
		boolean status = false;
		try {
			productRepo.deleteById((long) id);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on deleteProduct "+e.getMessage());
		}
		return status;
	}
	

	

	

	



}
