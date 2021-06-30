package com.mra.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mra.logger.ConfigProperties;
import com.mra.logger.MraLogger;
import com.mra.model.Category;
import com.mra.model.Collections;
import com.mra.model.Response;
import com.mra.service.CategoryService;
import com.mra.service.CollectionService;

@Component
public class CategoryBusiness {

	@Autowired
	MraLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	Response response;
	
	public Object getAllCategory() {
		List<Category> categoryList = categoryService.getAllCategory();
		if(categoryList!=null)
		{
			LOGGER.info(getClass(), "CATEGORIES RETRIEVED SUCCESSFULLY");
			response.setData(categoryList);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE CATEGORIES");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object getCategory(int id) {
		LOGGER.debug(getClass(), "CATEGORY ID IN GET "+id);
		Optional<Category> categories = categoryService.getCategory(id);
		if(categories.isPresent())
		{
			LOGGER.info(getClass(), "CATEGORY BY ID RETRIEVED SUCCESSFULLY");
			response.setData(categories.get());
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE CATEGORY FOR GIVEN ID");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object addCategory(Category category) {
		if(categoryService.addCategory(category))
		{
			LOGGER.info(getClass(), "CATEGORY ADDED SUCCESSFULLY");
			response.setData(category);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO ADD CATEGORY");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object updateCategory(Category category) {
		if(categoryService.updateCategory(category))
		{
			LOGGER.info(getClass(), "CATEGORY UPDATED SUCCESSFULLY");
			response.setData(category);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO UPDATE CATEGORY");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object deleteCategory(int id) {
		LOGGER.debug(getClass(), "CATEGORY ID IN DELETE "+id);
		if(categoryService.deleteCategory(id))
		{
			LOGGER.info(getClass(), "CATEGORY DELETED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO DELETE CATEGORY");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	
	
}
