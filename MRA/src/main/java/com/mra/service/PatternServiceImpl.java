package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mra.logger.MraLogger;
import com.mra.model.Category;
import com.mra.model.Collections;
import com.mra.model.Patterns;
import com.mra.repository.CategoryRepository;
import com.mra.repository.CollectionsRepository;
import com.mra.repository.PatternRepository;

@Component
public class PatternServiceImpl implements PatternService {

	@Autowired
	PatternRepository patternRepo;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public boolean addPattern(Patterns patterns) {
		boolean status = false;
		try {
			patternRepo.save(patterns);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addPattern "+e.getMessage());
		}
		return status;
	}

	@Override
	public List<Patterns> getAllPattern() {
		try {
			return (List<Patterns>) patternRepo.findAll();
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllPattern "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<Patterns> getPattern(int id) {
		try {
			return patternRepo.findById((long) id);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getPattern "+e.getMessage());
		}
		return null;
	}

	@Override
	public boolean updatePattern(Patterns patterns) {
		boolean status = false;
		try {
			patterns.setCreated(patternRepo.findById(patterns.getId()).get().getCreated());
			patternRepo.save(patterns);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on updatePattern "+e.getMessage());
		}
		return status;
	}

	@Override
	public boolean deletePattern(int id) {
		boolean status = false;
		try {
			patternRepo.deleteById((long) id);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on deletePattern "+e.getMessage());
		}
		return status;
	}

}
