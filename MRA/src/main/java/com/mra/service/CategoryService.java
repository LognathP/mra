package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mra.model.Category;

@Service
public interface CategoryService {

	List<Category> getAllCategory();

	Optional<Category> getCategory(int id);

	boolean addCategory(Category category);

	boolean deleteCategory(int id);

	boolean updateCategory(Category category);

}
