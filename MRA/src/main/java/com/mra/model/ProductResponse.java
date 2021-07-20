package com.mra.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
	
	Long id;
	String product_name;
	String product_desc;
	String colors;
	String patterns;
	double price;
	double selling_price;
	int active;
	int units;
	String collection;
	String category;
	private String file_type;
	String url;
	

}
