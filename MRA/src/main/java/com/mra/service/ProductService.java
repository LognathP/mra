package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mra.model.Category;
import com.mra.model.Patterns;
import com.mra.model.Product;

@Service
public interface ProductService {

	List<Product> getAllProduct();

	Optional<Product> getProduct(int id);

	boolean addProduct(Product product);

	boolean deleteProduct(int id);

}
