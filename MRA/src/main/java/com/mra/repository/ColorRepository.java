package com.mra.repository;

import org.springframework.data.repository.CrudRepository;

import com.mra.model.Category;
import com.mra.model.Color;

public interface ColorRepository extends CrudRepository<Color, Long>{

}
