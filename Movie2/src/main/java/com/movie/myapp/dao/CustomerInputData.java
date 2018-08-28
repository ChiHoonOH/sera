package com.movie.myapp.dao;

import com.movie.myapp.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CustomerInputData{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void insertCustomerData(Customer cust) {
		
		
		String sql = "SELECT MAX(customer_num) FROM customer_table";
		Integer num = 0;
		num = this.jdbcTemplate.queryForInt(sql);
		
		sql = "insert into customer_table (customer_num, id, pass, gender, age)" +
		"values(?,?,?,?,?)";
		jdbcTemplate.update(sql,
				++num,
				cust.getId(),
				cust.getPass(),
				cust.getGender(),
				cust.getAge()
				);
	}
}

