package com.mra.constants;

public interface OrderQueryConstants {
	
	String GET_ORDER_LIST = "select ucod.order_id,ucod.order_customer_name,ucod.order_address, " + 
			"ucod.order_contact_number,ucod.order_pincode,ucod.order_status,get_order_type(ucod.order_id) " + 
			"from uns_customer_order_detl ucod where ucod.order_placed_date between date(?) and date(?)";
	
	String GET_ORDER_ITEM = "select uoid.order_quantity,uoid.order_unit,upm.product_name ,usm.sub_product_name from uns_order_item_detl uoid,"
			+ "uns_product_mast upm,uns_subproduct_mast usm " + 
			"where uoid.order_id = ? and upm.product_id = uoid.product_id and usm.sub_product_id = uoid.sub_product_id";
	
	String GET_ORDER_DETAILS = "select order_price,order_status from uns_customer_order_detl ucod where ucod.order_id = ?";
	
	String UPDATE_ORDER = "update uns_customer_order_detl set order_status = ?, order_price = ?,order_updated_date = current_date where order_id = ?";
	
	String GET_ORDER_INVOICE = "select ucd.display_name,ucd.company_name,ucd.fisrt_name,ucd.email_id,ucd.mobile_phone, " + 
			"ucd.gst_identification_number_gstin,ucod.order_address from uns_customer_detl ucd,uns_customer_order_detl ucod where " + 
			"ucd.customer_id = ucod.order_customer_id and ucod.order_status = 'C' and ucod.order_placed_date between date(?) and date(?) ";
}
