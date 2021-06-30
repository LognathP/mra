package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mra.logger.MraLogger;
import com.mra.model.Color;
import com.mra.repository.ColorRepository;

@Component
public class ColorServiceImpl implements ColorService {

	@Autowired
	ColorRepository colorRepo;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public List<Color> getAllColor() {
		try {
			return (List<Color>) colorRepo.findAll();
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getAllColor "+e.getMessage());
		}
		return null;
	}

	@Override
	public Optional<Color> getColor(int id) {
		try {
			return colorRepo.findById((long) id);
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on getColor "+e.getMessage());
		}
		return null;
		
	}

	@Override
	public boolean addColor(Color color) {
		boolean status = false;
		try {
			colorRepo.save(color);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addColor "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean updateColor(Color color) {
		boolean status = false;
		try {
			color.setCreated(colorRepo.findById(color.getId()).get().getCreated());
			colorRepo.save(color);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on addColor "+e.getMessage());
		}
		return status;
	}
	
	@Override
	public boolean deleteColor(int id) {
		boolean status = false;
		try {
			colorRepo.deleteById((long) id);
			status = true;
		} catch (Exception e) {
			logger.error(getClass(), "Error Occured on deleteColor "+e.getMessage());
		}
		return status;
	}

}
