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
public class Order {

	int order_id;
	
	int order_customer_id;
	
	String order_customer_name;
	
	String order_address;
	
	String order_contact_number;
	
	String order_pincode;
	
	String order_status;
	
	String order_placed_date;
	
	String order_updated_date;
	
	int order_user_type;

	String from_date;
	
	String to_date;
	
	int orders_type;
	
	int order_price;
	
	
	
	
	
}
