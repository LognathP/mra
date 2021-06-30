package com.mra.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

	Long id;
	String product_name;
	String product_desc;
	List<String> colors;
	List<String> patterns;
	String image_path;
	double price;
	double selling_price;
	int active;
	int units;
	List<String> collection;
	List<String> category;
	MultipartFile file;
	
	
}
