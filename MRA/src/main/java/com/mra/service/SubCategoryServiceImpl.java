package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mra.logger.MraLogger;
import com.mra.model.SubCategory;
import com.mra.repository.SubCategoryRepository;

@Component
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
	SubCategoryRepository subCategoryRepo;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public List<SubCategory> getAllSubCategory() {
		try {
			return (List<SubCategory>) subCategoryRepo.findAll();
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllSubCategory "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<SubCategory> getSubCategory(int id) {
		try {
			return subCategoryRepo.findById((long) id);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getSubCategory "+e.getMessage());
		}
		return null;
		
	}

	@Override
	public boolean addSubCategory(SubCategory subCategory) {
		boolean status = false;
		try {
			subCategoryRepo.save(subCategory);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addSubCategory "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean updateSubCategory(SubCategory subCategory) {
		boolean status = false;
		try {
			subCategory.setCreated(subCategoryRepo.findById(subCategory.getId()).get().getCreated());
			subCategoryRepo.save(subCategory);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on updateSubCategory "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean deleteSubCategory(int id) {
		boolean status = false;
		try {
			subCategoryRepo.deleteById((long) id);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on deleteSubCategory "+e.getMessage());
		}
		return status;
	}

}
