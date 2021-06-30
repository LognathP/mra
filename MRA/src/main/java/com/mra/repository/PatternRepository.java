package com.mra.repository;

import org.springframework.data.repository.CrudRepository;

import com.mra.model.Category;
import com.mra.model.Patterns;

public interface PatternRepository extends CrudRepository<Patterns, Long>{

}
