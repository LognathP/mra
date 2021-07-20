package com.mra.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		product.setId(prodRequest.getId());
		return product;
	}
	
	
	
	public static List<ProductResponse> prepareResponseList(List<Product> productList)
	{
		List<ProductResponse> productResponseList = new ArrayList<ProductResponse>();
		for (Product product : productList) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setCategory(Stream.of(product.getCategory().split(","))
					.collect(Collectors.toList()).toString());
			productResponse.setCollection(Stream.of(product.getCollection().split(","))
			        .collect(Collectors.toList()).toString());
			productResponse.setColors(Stream.of(product.getColors().split(","))
			        .collect(Collectors.toList()).toString());
			productResponse.setPatterns(Stream.of(product.getPatterns().split(","))
			        .collect(Collectors.toList()).toString());
			productResponse.setProduct_name(product.getProduct_name());
			productResponse.setProduct_desc(product.getProduct_desc());
			productResponse.setUnits(product.getUnits());
			productResponse.setSelling_price(product.getSelling_price());
			productResponse.setPrice(product.getPrice());
			productResponse.setUrl("/api/product/download/"+product.getId());
			productResponse.setId(product.getId());
			productResponseList.add(productResponse);
		}
		return productResponseList;
	}
	
	public static ProductResponse prepareResponse(Product product)
	{
		
			ProductResponse productResponse = new ProductResponse();
			productResponse.setCategory(Stream.of(product.getCategory().split(","))
					.collect(Collectors.toList()).toString());
			productResponse.setCollection(Stream.of(product.getCollection().split(","))
			        .collect(Collectors.toList()).toString());
			productResponse.setColors(Stream.of(product.getColors().split(","))
			        .collect(Collectors.toList()).toString());
			productResponse.setPatterns(Stream.of(product.getPatterns().split(","))
			        .collect(Collectors.toList()).toString());
			productResponse.setProduct_name(product.getProduct_name());
			productResponse.setProduct_desc(product.getProduct_desc());
			productResponse.setUnits(product.getUnits());
			productResponse.setSelling_price(product.getSelling_price());
			productResponse.setPrice(product.getPrice());
			productResponse.setUrl("/api/product/download/"+product.getId());
			productResponse.setId(product.getId());
		
		return productResponse;
	}
}
