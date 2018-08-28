package com.movie.myapp.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

public class CustomerPrintData {
	
	@Autowired
	JdbcTemplate jdbcTemplate;	

	public int printCustomerData(String id,String pass) {		
		String sql ="select customer_num from customer_table where id=? and pass=?";		
		int customer_num=0;	
		try {
		customer_num=jdbcTemplate.queryForInt(sql, id, pass);
		}catch(Exception e) {}
		//System.out.println(customer_num);
		return customer_num;
	}
	

	
}
