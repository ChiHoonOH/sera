package com.movie.myapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.movie.myapp.model.ConditionTable;
import com.movie.myapp.model.Movie;
import com.movie.myapp.model.Movie2;


public class MovieRecommend{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Movie2> movieRecommend (int customer_num)
	{		
		String sql;		
		ConditionTable targetTable = new ConditionTable();
		List<ConditionTable> tableList = this.getAllTableInfo("gender_age_table");
		for(ConditionTable table : tableList)
		{
			targetTable = table;
			sql = "select count(customer_num) from (select * from customer_table where customer_num = ?) where "+table.getCondition();
			int num = this.jdbcTemplate.queryForInt(sql, customer_num);
			if(num > 0) { break;}
		}	
		List<Movie2> movie = this.getAllRecInfo(targetTable.getName());
		
		return movie;
	}
	
	public List<ConditionTable> getAllTableInfo(String table)
	{
		String sql ="select table_name ,table_condition from "+table; 
		return  jdbcTemplate.query(sql,				  
				  new RowMapper<ConditionTable>() {
				    public ConditionTable mapRow(ResultSet rs, int rowNum) throws SQLException {
				    	ConditionTable mtable = new ConditionTable();
				    	mtable.setName(rs.getString("table_name"));
				    	mtable.setCondition(rs.getString("table_condition"));
				     return mtable;
				    }
				  });		
	}

	public List<Movie2> getAllRecInfo(String name)
	{
		String sql ="select movie_name, count from " +name+ ", movie_table m where "+name+".movie_num = m.movie_num order by count desc"; 
		return  jdbcTemplate.query(sql,				  
				  new RowMapper<Movie2>() {
				    public Movie2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				    Movie2 movie = new Movie2();
				    movie.setMovie_name(rs.getString("movie_name"));
				    movie.setMovie_num(rs.getInt("count"));
				     return movie;
				    }
				  });
		
	}
}
