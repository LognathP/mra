package com.mra.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mra.business.CategoryBusiness;
import com.mra.logger.MraLogger;
import com.mra.model.Category;

@RestController
public class CategoryController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	MraLogger Logger;
	
	@Autowired
	CategoryBusiness categoryBusiness;

	@GetMapping("/api/category/getall")
	public Object getAllCategory() {
		Logger.info(this.getClass(),"GET ALL CATEGORIES API CALL STARTED AT "+dateFormat.format(new Date()));
		return categoryBusiness.getAllCategory();
	}
	@GetMapping("/api/category/get/{id}")
	public Object getCategory(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"GET CATEGORY BY ID API CALL STARTED AT "+dateFormat.format(new Date()));
		return categoryBusiness.getCategory(id);
	}
	@PostMapping("/api/category/add")
	public Object addCategory(@RequestBody Category category) {
		Logger.info(this.getClass(),"ADD CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return categoryBusiness.addCategory(category);
	}
	@PutMapping("/api/category/update")
	public Object updateCategory(@RequestBody Category category) {
		Logger.info(this.getClass(),"UPDATE CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return categoryBusiness.updateCategory(category);
	}
	@DeleteMapping("/api/category/delete/{id}")
	public Object deleteCategory(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"DELETE CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return categoryBusiness.deleteCategory(id);
	}
	
	
}
