package com.movie.myapp.dao;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import com.movie.myapp.model.Customer;


public class InsertCustomer{
	
	@Autowired
	JdbcTemplate  jdbcTemplate;
	
	public void insertCustomerData(Customer cust) {	
		String sql="insert into customer(customer_num,id,"
				+"pass,gender,age)"
				+"values(?,?,?,?,?)";		
		jdbcTemplate.update(sql,
				cust.getCustomerNum(),
				cust.getId(),
				cust.getGender(),
				cust.getAge());			
	}


}
