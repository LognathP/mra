package com.mra.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mra.business.SubCategoryBusiness;
import com.mra.logger.MraLogger;
import com.mra.model.SubCategory;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SubCategoryController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	MraLogger Logger;
	
	@Autowired
	SubCategoryBusiness subSubCategoryBusiness;

	@GetMapping("/api/subcategory/getall")
	public Object getAllSubCategory() {
		Logger.info(this.getClass(),"GET ALL SUB CATEGORIES API CALL STARTED AT "+dateFormat.format(new Date()));
		return subSubCategoryBusiness.getAllSubCategory();
	}
	@GetMapping("/api/subcategory/get/{id}")
	public Object getSubCategory(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"GET SUB CATEGORY BY ID API CALL STARTED AT "+dateFormat.format(new Date()));
		return subSubCategoryBusiness.getSubCategory(id);
	}
	@PostMapping("/api/subcategory/add")
	public Object addSubCategory(@RequestBody SubCategory subcategory) {
		Logger.info(this.getClass(),"ADD SUB CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return subSubCategoryBusiness.addSubCategory(subcategory);
	}
	@PutMapping("/api/subcategory/update")
	public Object updateSubCategory(@RequestBody SubCategory subcategory) {
		Logger.info(this.getClass(),"UPDATE SUB CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return subSubCategoryBusiness.updateSubCategory(subcategory);
	}
	@DeleteMapping("/api/subcategory/delete/{id}")
	public Object deleteSubCategory(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"DELETE SUB CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return subSubCategoryBusiness.deleteSubCategory(id);
	}
	
	
}
