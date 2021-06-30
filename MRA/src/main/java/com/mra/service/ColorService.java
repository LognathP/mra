package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mra.model.Color;

@Service
public interface ColorService {

	List<Color> getAllColor();

	Optional<Color> getColor(int id);

	boolean addColor(Color color);

	boolean deleteColor(int id);

	boolean updateColor(Color color);

}
