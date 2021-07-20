package com.mra.repository;

import org.springframework.data.repository.CrudRepository;

import com.mra.model.Category;
import com.mra.model.SubCategory;

public interface SubCategoryRepository extends CrudRepository<SubCategory, Long>{

}
