package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mra.model.Category;
import com.mra.model.Patterns;

@Service
public interface PatternService {

	boolean addPattern(Patterns patterns);

	List<Patterns> getAllPattern();

	Optional<Patterns> getPattern(int id);

	boolean updatePattern(Patterns patterns);

	boolean deletePattern(int id);

}
