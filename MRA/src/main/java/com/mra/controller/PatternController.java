package com.mra.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mra.business.PatternBusiness;
import com.mra.logger.MraLogger;
import com.mra.model.Patterns;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PatternController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	MraLogger Logger;
	
	@Autowired
	PatternBusiness patternBusiness;

	@GetMapping("/api/pattern/getall")
	public Object getAllPattern() {
		Logger.info(this.getClass(),"GET ALL PATTERNS API CALL STARTED AT "+dateFormat.format(new Date()));
		return patternBusiness.getAllPattern();
	}
	@GetMapping("/api/pattern/get/{id}")
	public Object getPattern(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"GET PATTERN BY ID API CALL STARTED AT "+dateFormat.format(new Date()));
		return patternBusiness.getPattern(id);
	}
	@PostMapping("/api/pattern/add")
	public Object addPattern(@RequestParam("file") MultipartFile file,@RequestParam("name") String name,@RequestParam("id") int id) throws Exception {
		Logger.info(this.getClass(),"ADD PATTERN API CALL STARTED AT "+dateFormat.format(new Date()));
		return patternBusiness.addPattern(file,name,id);
	}
	@PutMapping("/api/pattern/update")
	public Object updatePattern(@RequestParam("file") MultipartFile file,@RequestParam("name") String name,@RequestParam("id") int id) throws Exception {
		Logger.info(this.getClass(),"UPDATE PATTERN API CALL STARTED AT "+dateFormat.format(new Date()));
		return patternBusiness.updatePattern(file,name,id);
	}
	@DeleteMapping("/api/pattern/delete/{id}")
	public Object deletePattern(@PathVariable ("id") int id) {
		Logger.info(this.getClass(),"DELETE PATTERN API CALL STARTED AT "+dateFormat.format(new Date()));
		return patternBusiness.deletePattern(id);
	}
	@GetMapping("/api/pattern/download/{fileId}")
	public ResponseEntity<Resource> downloadPattern(@PathVariable int fileId) throws Exception {
		Logger.info(this.getClass(),"DOWNLOAD PATTERN API CALL STARTED AT "+dateFormat.format(new Date()));
		return patternBusiness.downloadPattern(fileId);   
	}
	
	
	
}
