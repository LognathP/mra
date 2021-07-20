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

import com.mra.business.CollectionBusiness;
import com.mra.logger.MraLogger;
import com.mra.model.Collections;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CollectionController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	MraLogger Logger;
	
	@Autowired
	CollectionBusiness productBusiness;

	@GetMapping("/api/collection/getall")
	public Object getAllCollection() {
		Logger.info(this.getClass(),"GET ALL COLLECTIONS API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.getAllCollection();
	}
	@GetMapping("/api/collection/get/{id}")
	public Object getCollection(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"GET COLLECTION BY ID API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.getCollection(id);
	}
	@PostMapping("/api/collection/add")
	public Object addCollection(@RequestBody Collections collection) {
		Logger.info(this.getClass(),"ADD COLLECTIONS API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.addCollection(collection);
		
	}
	@PutMapping("/api/collection/update")
	public Object updateCollection(@RequestBody Collections collection) {
		Logger.info(this.getClass(),"UPDATE COLLECTIONS API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.updateCollection(collection);
	}
	@DeleteMapping("/api/collection/delete/{id}")
	public Object deleteCollection(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"DELETE COLLECTION API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.deleteCollection(id);
	}
	
	
}
