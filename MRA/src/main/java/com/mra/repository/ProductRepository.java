package com.mra.repository;

import org.springframework.data.repository.CrudRepository;

import com.mra.model.Category;
import com.mra.model.Patterns;
import com.mra.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
