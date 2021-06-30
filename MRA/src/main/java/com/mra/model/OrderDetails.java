package com.mra.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonInclude(Include.NON_EMPTY)

public class OrderDetails {

	int product_id;
	
	String product_name;
	
	int sub_product_id;
	
	String sub_product_name;
	
	int order_unit;
	
	int order_quantity;
	
	int order_id;
	
	String order_status;
	
}
