package com.mra.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mra.logger.ConfigProperties;
import com.mra.logger.MraLogger;
import com.mra.model.Color;
import com.mra.model.Collections;
import com.mra.model.Response;
import com.mra.service.ColorService;

@Component
public class ColorBusiness {

	@Autowired
	MraLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	ColorService colorService;
	
	@Autowired
	Response response;
	
	public Object getAllColor() {
		List<Color> colorList = colorService.getAllColor();
		if(colorList!=null)
		{
			LOGGER.info(getClass(), "COLORS RETRIEVED SUCCESSFULLY");
			response.setData(colorList);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE COLORS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object getColor(int id) {
		LOGGER.debug(getClass(), "COLOR ID IN GET "+id);
		Optional<Color> categories = colorService.getColor(id);
		if(categories.isPresent())
		{
			LOGGER.info(getClass(), "COLOR BY ID RETRIEVED SUCCESSFULLY");
			response.setData(categories.get());
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE COLOR FOR GIVEN ID");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object addColor(Color category) {
		if(colorService.addColor(category))
		{
			LOGGER.info(getClass(), "COLOR ADDED SUCCESSFULLY");
			response.setData(category);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO ADD COLOR");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object updateColor(Color category) {
		if(colorService.updateColor(category))
		{
			LOGGER.info(getClass(), "COLOR UPDATED SUCCESSFULLY");
			response.setData(category);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO UPDATE COLOR");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object deleteColor(int id) {
		LOGGER.debug(getClass(), "COLOR ID IN DELETE "+id);
		if(colorService.deleteColor(id))
		{
			LOGGER.info(getClass(), "COLOR DELETED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO DELETE COLOR");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	
	
}
