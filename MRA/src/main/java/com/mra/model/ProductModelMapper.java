package com.mra.model;

public class ProductModelMapper {

	public static Product productModelDto(ProductRequest prodRequest)
	{
		Product product = new Product();
		product.setProduct_name(prodRequest.getProduct_name());
		product.setProduct_desc(prodRequest.getProduct_desc());
		product.setCategory(String.join(",", prodRequest.getCategory()));
		product.setCollection(String.join(",", prodRequest.getCollection()));
		product.setColors(String.join(",", prodRequest.getColors()));
		product.setPatterns(String.join(",", prodRequest.getPatterns()));
		product.setPrice(prodRequest.getPrice());
		product.setSelling_price(prodRequest.getSelling_price());
		product.setUnits(prodRequest.getUnits());
		return product;
	}
}
