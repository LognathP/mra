package com.mra.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mra.logger.ConfigProperties;
import com.mra.logger.MraLogger;
import com.mra.model.SubCategory;
import com.mra.model.Collections;
import com.mra.model.Response;
import com.mra.service.SubCategoryService;
import com.mra.service.CollectionService;

@Component
public class SubCategoryBusiness {

	@Autowired
	MraLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	SubCategoryService subCategoryService;
	
	@Autowired
	Response response;
	
	public Object getAllSubCategory() {
		List<SubCategory> categoryList = subCategoryService.getAllSubCategory();
		if(categoryList!=null)
		{
			LOGGER.info(getClass(), "SUB CATEGORIES RETRIEVED SUCCESSFULLY");
			response.setData(categoryList);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE SUB CATEGORIES");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object getSubCategory(int id) {
		LOGGER.debug(getClass(), "SUB SUB CATEGORY ID IN GET "+id);
		Optional<SubCategory> categories = subCategoryService.getSubCategory(id);
		if(categories.isPresent())
		{
			LOGGER.info(getClass(), "SUB SUB CATEGORY BY ID RETRIEVED SUCCESSFULLY");
			response.setData(categories.get());
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE SUB SUB CATEGORY FOR GIVEN ID");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object addSubCategory(SubCategory category) {
		if(subCategoryService.addSubCategory(category))
		{
			LOGGER.info(getClass(), "SUB SUB CATEGORY ADDED SUCCESSFULLY");
			response.setData(category);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO ADD SUB SUB CATEGORY");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object updateSubCategory(SubCategory category) {
		if(subCategoryService.updateSubCategory(category))
		{
			LOGGER.info(getClass(), "SUB SUB CATEGORY UPDATED SUCCESSFULLY");
			response.setData(category);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO UPDATE SUB SUB CATEGORY");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object deleteSubCategory(int id) {
		LOGGER.debug(getClass(), "SUB SUB CATEGORY ID IN DELETE "+id);
		if(subCategoryService.deleteSubCategory(id))
		{
			LOGGER.info(getClass(), "SUB SUB CATEGORY DELETED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO DELETE SUB SUB CATEGORY");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	
	
}
