package com.movie.myapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.movie.myapp.model.ConditionTable;
import com.movie.myapp.model.Movie;
import com.movie.myapp.model.TicketOrder;

public class AnalysisTicketOrder{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void run()
	{
		String sql;
		List<ConditionTable> tableList = this.getAllTableInfo("gender_age_table");
		List<Movie> movieList = this.getAllMovieInfo();
		
		for(ConditionTable table :tableList)
		{
			sql = "delete from "+table.getName();
			this.jdbcTemplate.update(sql);
			
			for(Movie movie: movieList) {
				
				sql = "select count(order_num) from "
						+ "(select * from ticket_order_table t, movie_table m, customer_table c where t.customer_num = c.customer_num and t.movie_list_num=m.movie_num)"
						+ "where " + table.getCondition()+ " and movie_name=?";
				int count = this.jdbcTemplate.queryForInt(sql, movie.getMovieName());
				
				sql = "insert into " + table.getName() + "(movie_num, count) values(?,?)";
				this.jdbcTemplate.update(sql, movie.getMovieNum(), count);
			}
		}
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
	
	public List<Movie> getAllMovieInfo()
	{
		String sql ="select movie_num ,movie_name from movie_table"; 
		return  jdbcTemplate.query(sql,				  
				  new RowMapper<Movie>() {
				    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				    Movie movie = new Movie();
				    movie.setMovieName(rs.getString("movie_name"));
				    movie.setMovieNum(rs.getInt("movie_num"));
				     return movie;
				    }
				  });
		
	}
	
	public List<TicketOrder> getAllOrderInfo()
	{
		String sql ="select order_num, movie_list_num, customer_num, time from ticket_order_table"; 
		return  jdbcTemplate.query(sql,				  
		  new RowMapper<TicketOrder>() {
		    public TicketOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	TicketOrder ticket = new TicketOrder();
		    	ticket.setTicketNum(rs.getString("order_num"));
		    	ticket.setMovieListNum(rs.getInt("movie_list_num"));
		    	ticket.setCustomerNum(rs.getInt("customer_num"));
		    	ticket.setTime(rs.getString("time"));
		     return ticket;
		    }
		  });
	}	

}


