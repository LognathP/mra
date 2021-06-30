package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mra.logger.MraLogger;
import com.mra.model.Category;
import com.mra.model.Collections;
import com.mra.repository.CategoryRepository;
import com.mra.repository.CollectionsRepository;

@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public List<Category> getAllCategory() {
		try {
			return (List<Category>) categoryRepo.findAll();
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllCategory "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<Category> getCategory(int id) {
		try {
			return categoryRepo.findById((long) id);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getCategory "+e.getMessage());
		}
		return null;
		
	}

	@Override
	public boolean addCategory(Category category) {
		boolean status = false;
		try {
			categoryRepo.save(category);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addCategory "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean updateCategory(Category category) {
		boolean status = false;
		try {
			category.setCreated(categoryRepo.findById(category.getId()).get().getCreated());
			categoryRepo.save(category);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addCategory "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean deleteCategory(int id) {
		boolean status = false;
		try {
			categoryRepo.deleteById((long) id);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on deleteCategory "+e.getMessage());
		}
		return status;
	}

}
