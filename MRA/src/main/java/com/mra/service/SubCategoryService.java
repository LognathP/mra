package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mra.model.Category;
import com.mra.model.SubCategory;

@Service
public interface SubCategoryService {

	List<SubCategory> getAllSubCategory();

	Optional<SubCategory> getSubCategory(int id);

	boolean addSubCategory(SubCategory subCategory);

	boolean deleteSubCategory(int id);

	boolean updateSubCategory(SubCategory subCategory);

}
