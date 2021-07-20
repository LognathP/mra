package com.mra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mra.constants.MRAQueryConstants;
import com.mra.logger.MraLogger;
import com.mra.model.Address;
import com.mra.utils.Utils;

@Component
public class AddressDaoImpl implements AddressDao{

	@Autowired
	JdbcTemplate jdbctemp;
	
	@Autowired
	MraLogger logger;
	
	@Override
	public List<Address> getAddressByCustomer(int customerId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<Address> addressList = new ArrayList<Address>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(MRAQueryConstants.GET_ADDRESS_BY_CUSTOMER);
			preStmt.setInt(1,customerId);
			res = preStmt.executeQuery();
			if (res.next()) {
				Address address = new Address();
				address.setId((long)res.getInt("id"));
				address.setAddress_one(res.getString("address_one"));
				address.setAddress_two(res.getString("address_two"));
				address.setAddress_three(res.getString("address_three"));
				address.setCustomer_id(res.getInt("customer_id"));
				address.setPincode(res.getString("pincode"));
				address.setState(res.getString("state"));
				address.setCountry(res.getString("country"));
				addressList.add(address);
			}

		} catch (Exception e) {
			logger.debug(this.getClass(), "ERROR IN DB WHILE getAddressByCustomer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				Utils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getAddressByCustomer " + e.getMessage());
			}

		}
		return addressList;
	}

}
