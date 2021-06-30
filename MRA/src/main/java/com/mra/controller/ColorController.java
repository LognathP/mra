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

import com.mra.business.ColorBusiness;
import com.mra.business.ColorBusiness;
import com.mra.logger.MraLogger;
import com.mra.model.Color;

@RestController
public class ColorController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	MraLogger Logger;
	
	@Autowired
	ColorBusiness colorBusiness;

	@GetMapping("/api/color/getall")
	public Object getAllColor() {
		Logger.info(this.getClass(),"GET ALL COLORS API CALL STARTED AT "+dateFormat.format(new Date()));
		return colorBusiness.getAllColor();
	}
	@GetMapping("/api/color/get/{id}")
	public Object getColor(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"GET COLOR BY ID API CALL STARTED AT "+dateFormat.format(new Date()));
		return colorBusiness.getColor(id);
	}
	@PostMapping("/api/color/add")
	public Object addColor(@RequestBody Color color) {
		Logger.info(this.getClass(),"ADD COLOR API CALL STARTED AT "+dateFormat.format(new Date()));
		return colorBusiness.addColor(color);
	}
	@PutMapping("/api/color/update")
	public Object updateColor(@RequestBody Color color) {
		Logger.info(this.getClass(),"UPDATE COLOR API CALL STARTED AT "+dateFormat.format(new Date()));
		return colorBusiness.updateColor(color);
	}
	@DeleteMapping("/api/color/delete/{id}")
	public Object deleteColor(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"DELETE COLOR API CALL STARTED AT "+dateFormat.format(new Date()));
		return colorBusiness.deleteColor(id);
	}
	
	
}
