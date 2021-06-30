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
			for (Patterns patterns : patternList) {
				patterns.setPattern_file(getDownloadUri(patterns.getPattern_file()));
			}
			LOGGER.info(getClass(), "PATTERNS RETRIEVED SUCCESSFULLY");
			response.setData(patternList);
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
			Patterns pattern = patterns.get();
			pattern.setPattern_file(getDownloadUri(pattern.getPattern_file()));
			LOGGER.info(getClass(), "PATTERN BY ID RETRIEVED SUCCESSFULLY");
			response.setData(pattern);
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
			String fileName = storeFile(file);	
			patterns.setPattern_name(name);
			patterns.setFile_size((int)file.getSize());
			patterns.setPattern_file(fileName);
			patterns.setId((long) id);
	     }
		else
		{
			Patterns oldPattern = patternService.getPattern(id).get();
			patterns.setPattern_name(name);
			patterns.setFile_size(oldPattern.getFile_size());
			patterns.setPattern_file(oldPattern.getPattern_file());
			patterns.setId((long) id);
		}   
		if(patternService.addPattern(patterns))
		{
			LOGGER.info(getClass(), "PATTERN ADDED SUCCESSFULLY");
			response.setData(patterns);
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
			String fileName = storeFile(file);	
			patterns.setPattern_name(name);
			patterns.setFile_size((int)file.getSize());
			patterns.setPattern_file(fileName);
			patterns.setId((long) id);
	     }
		else
		{
			Patterns oldPattern = patternService.getPattern(id).get();
			patterns.setPattern_name(name);
			patterns.setFile_size(oldPattern.getFile_size());
			patterns.setPattern_file(oldPattern.getPattern_file());
			patterns.setId((long) id);
		}        
		if(patternService.updatePattern(patterns))
		{
			LOGGER.info(getClass(), "PATTERN UPDATED SUCCESSFULLY");
			response.setData(patterns);
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
	
	public String storeFile(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filepath = Paths.get(env.getProperty("file.upload.pattern"))
        .toAbsolutePath().normalize();
        Files.createDirectories(filepath);
        try {
            if(fileName.contains("..")) {
                throw new Exception("Sorry! Filename is invalid " + fileName);
            }
            Path targetLocation = filepath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws Exception {
    	 Path filepath = Paths.get(env.getProperty("file.upload.pattern"))
    		        .toAbsolutePath().normalize();
        try {
            Path filePath = filepath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("File Not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File Not found " + fileName, ex);
        }
    }

	public ResponseEntity<Resource> downloadPattern(String fileName, HttpServletRequest request) throws Exception {
        Resource resource = loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
		
	}
	
	public String getDownloadUri(String fileName)
	{
		return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/pattern/download/")
                .path(fileName)
                .toUriString();
	}
    
    
    
}
