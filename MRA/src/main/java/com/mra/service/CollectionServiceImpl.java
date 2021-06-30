package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mra.logger.MraLogger;
import com.mra.model.Collections;
import com.mra.repository.CollectionsRepository;

@Component
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	CollectionsRepository collectionRepo;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public List<Collections> getAllCollection() {
		try {
			return (List<Collections>) collectionRepo.findAll();
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllCollection "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<Collections> getCollection(int id) {
		try {
			return collectionRepo.findById((long) id);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllCollection "+e.getMessage());
		}
		return null;
		
	}

	@Override
	public boolean addCollection(Collections collection) {
		boolean status = false;
		try {
			collectionRepo.save(collection);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addCollection "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean updateCollection(Collections collection) {
		boolean status = false;
		try {
			collection.setCreated(collectionRepo.findById(collection.getId()).get().getCreated());
			collectionRepo.save(collection);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addCollection "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean deleteCollection(int id) {
		boolean status = false;
		try {
			collectionRepo.deleteById((long) id);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on deleteCollection "+e.getMessage());
		}
		return status;
	}

}
