package com.movie.myapp.dao;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import com.movie.myapp.model.Customer;

public class CustomerPrintAllData {
	
	@Autowired
	JdbcTemplate jdbcTemplate;	
	
	public Customer printCustomerAllData(int customer_num) {
		
		String sql ="select customer_num, id, pass, gender, age from customer_table where customer_num=?";		
		
	 
		
		Customer cust = this.jdbcTemplate.queryForObject(sql,				  
				  new RowMapper<Customer>() {
				    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
				      Customer cust = new Customer();
				      cust.setCustomerNum(rs.getInt("customer_num"));
				      cust.setId(rs.getString("id"));
				      cust.setPass(rs.getString("pass"));
				      cust.setGender(rs.getString("gender"));
				      cust.setAge(rs.getInt("age"));
				      return cust;
				    }
				  }, customer_num);
		return cust;
	}
	

	
}
