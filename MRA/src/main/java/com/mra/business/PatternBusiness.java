package com.mra.business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mra.logger.ConfigProperties;
import com.mra.logger.MraLogger;
import com.mra.model.Category;
import com.mra.model.Collections;
import com.mra.model.DBFile;
import com.mra.model.PatternModelMapper;
import com.mra.model.Patterns;
import com.mra.model.PatternsResponse;
import com.mra.model.Response;
import com.mra.service.CategoryService;
import com.mra.service.CollectionService;
import com.mra.service.PatternService;

@Component
public class PatternBusiness {

	@Autowired
	Environment env;
	
	@Autowired
	MraLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	PatternService patternService;
	
	@Autowired
	Response response;
	
	
	public Object getAllPattern() {
		List<Patterns> patternList = patternService.getAllPattern();
		if(patternList!=null)
		{
			
			LOGGER.info(getClass(), "PATTERNS RETRIEVED SUCCESSFULLY");
			response.setData(PatternModelMapper.prepareResponseList(patternList));
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE PATTERNS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object getPattern(int id) {
		LOGGER.debug(getClass(), "PATTERN ID IN GET "+id);
		Optional<Patterns> patterns = patternService.getPattern(id);
		if(patterns.isPresent())
		{
			LOGGER.info(getClass(), "PATTERN BY ID RETRIEVED SUCCESSFULLY");
			response.setData(PatternModelMapper.prepareResponse(patterns.get()));
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE PATTERN FOR GIVEN ID");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object addPattern(MultipartFile file,String name,int id) throws Exception {
		
		Patterns patterns = new Patterns();
		if(file.getSize()>0)
		{
			String fileName = storeFileDB(file);	
			patterns.setPattern_name(name);
			patterns.setFile_size((int)file.getSize());
			patterns.setPattern_file(fileName);
			patterns.setId((long) id);
			patterns.setData(file.getBytes());
			patterns.setFile_type(file.getContentType());
	     }
		else
		{
			Patterns oldPattern = patternService.getPattern(id).get();
			patterns.setPattern_name(name);
			patterns.setFile_size(oldPattern.getFile_size());
			patterns.setPattern_file(oldPattern.getPattern_file());
			patterns.setId((long) id);
			patterns.setFile_type(file.getContentType());
		}   
		if(patternService.addPattern(patterns))
		{
			LOGGER.info(getClass(), "PATTERN ADDED SUCCESSFULLY");
			response.setData(PatternModelMapper.prepareResponse(patterns));
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO ADD PATTERN");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object updatePattern(MultipartFile file,String name,int id) throws Exception {
		Patterns patterns = new Patterns();
		if(file.getSize()>0)
		{
			String fileName = storeFileDB(file);	
			patterns.setPattern_name(name);
			patterns.setFile_size((int)file.getSize());
			patterns.setPattern_file(fileName);
			patterns.setId((long) id);
			patterns.setFile_type(file.getContentType());
	     }
		else
		{
			Patterns oldPattern = patternService.getPattern(id).get();
			patterns.setPattern_name(name);
			patterns.setFile_size(oldPattern.getFile_size());
			patterns.setPattern_file(oldPattern.getPattern_file());
			patterns.setId((long) id);
			patterns.setFile_type(file.getContentType());
		}        
		if(patternService.updatePattern(patterns))
		{
			LOGGER.info(getClass(), "PATTERN UPDATED SUCCESSFULLY");
			response.setData(PatternModelMapper.prepareResponse(patterns));
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO UPDATE PATTERN");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object deletePattern(int id) {
		LOGGER.debug(getClass(), "PATTERN ID IN DELETE "+id);
		if(patternService.deletePattern(id))
		{
			LOGGER.info(getClass(), "PATTERN DELETED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO DELETE PATTERN");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
//	public String storeFile(MultipartFile file) throws Exception {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Path filepath = Paths.get(env.getProperty("file.upload.pattern"))
//        .toAbsolutePath().normalize();
//        Files.createDirectories(filepath);
//        try {
//            if(fileName.contains("..")) {
//                throw new Exception("Sorry! Filename is invalid " + fileName);
//            }
//            Path targetLocation = filepath.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return fileName;
//        } catch (IOException ex) {
//            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }
//	
	 public String storeFileDB(MultipartFile file) throws Exception {
	        // Normalize file name
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
	            }
           return fileName;
	        } catch (Exception ex) {
	            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
	        }
	    }

//    public Resource loadFileAsResource(String fileName) throws Exception {
//    	 Path filepath = Paths.get(env.getProperty("file.upload.pattern"))
//    		        .toAbsolutePath().normalize();
//        try {
//            Path filePath = filepath.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists()) {
//                return resource;
//            } else {
//                throw new Exception("File Not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new Exception("File Not found " + fileName, ex);
//        }
//    }
//
//	public ResponseEntity<Resource> downloadPattern(String fileName, HttpServletRequest request) throws Exception {
//        Resource resource = loadFileAsResource(fileName);
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//        }
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//		
//	}
//	
//	public String getDownloadUri(String fileName)
//	{
//		return ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/pattern/download/")
//                .path(fileName)
//                .toUriString();
//	}
	
	public ResponseEntity<Resource> downloadPattern(int fileId) throws Exception {
		 
		Optional<Patterns> patterns = patternService.getPattern(fileId);
		if(patterns.isPresent())
		{
			LOGGER.info(getClass(), "PATTERN FILE FOUND ");
			//LOGGER.info(getClass(), "PATTERN " + patterns.get().toString());
			Patterns pattern = patterns.get();
			 return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(pattern.getFile_type()))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pattern.getPattern_file()+ "\"")
		                .body(new ByteArrayResource(pattern.getData()));
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO FIND PATTERN");
			return ResponseEntity.notFound().build();
		}

	       
		
	}
    
    
    
}
