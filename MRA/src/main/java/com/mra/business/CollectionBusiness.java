package com.mra.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mra.logger.ConfigProperties;
import com.mra.logger.MraLogger;
import com.mra.model.Collections;
import com.mra.model.Response;
import com.mra.service.CollectionService;

@Component
public class CollectionBusiness {

	@Autowired
	MraLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	CollectionService productService;
	
	@Autowired
	Response response;
	
	public Object getAllCollection() {
		List<Collections> collectionList = productService.getAllCollection();
		if(collectionList!=null)
		{
			LOGGER.info(getClass(), "COLLECTIONS RETRIEVED SUCCESSFULLY");
			response.setData(collectionList);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE COLLECTIONS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object getCollection(int id) {
		LOGGER.debug(getClass(), "COLLECTION ID "+id);
		Optional<Collections> collections = productService.getCollection(id);
		if(collections.isPresent())
		{
			LOGGER.info(getClass(), "COLLECTION BY ID RETRIEVED SUCCESSFULLY");
			response.setData(collections.get());
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE COLLECTION FOR GIVEN ID");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object addCollection(Collections collection) {
		if(productService.addCollection(collection))
		{
			LOGGER.info(getClass(), "COLLECTION ADDED SUCCESSFULLY");
			response.setData(collection);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO ADD COLLECTION");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object updateCollection(Collections collection) {
		if(productService.updateCollection(collection))
		{
			LOGGER.info(getClass(), "COLLECTION UPDATED SUCCESSFULLY");
			response.setData(collection);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO UPDATE COLLECTION");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object deleteCollection(int id) {
		if(productService.deleteCollection(id))
		{
			LOGGER.info(getClass(), "COLLECTION DELETED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO DELETE COLLECTION");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	
	
}
