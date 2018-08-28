package com.movie.myapp.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class SelectReservation {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List selectReservation() {
	String sql = "SELECT * FROM ticket_order_table";	
	List list=jdbcTemplate.queryForList(sql);
	System.out.println("list:"+list);
	return list;
	}
	
}
