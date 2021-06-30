package com.mra.constants;

public interface ProductQueryConstants {
	
	String GET_SUBPRODUCT_LIST = "select usm.sub_product_id,usm.sub_product_name,usm.sub_product_desc,upm.product_name,usm.unit_metrics from uns_subproduct_mast usm,uns_product_mast upm where usm.product_id = upm.product_id";

	String GET_PROD_SUBPROD_MAP = "select count(1) from uns_subproduct_mast where product_id=?";
}
