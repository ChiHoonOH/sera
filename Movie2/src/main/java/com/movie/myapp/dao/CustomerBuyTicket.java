package com.movie.myapp.dao;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;




import com.movie.myapp.model.Movie2;
import com.movie.myapp.model.TicketOrder;
import com.movie.myapp.mybatis.CustomerBuyTicketMapper;





public class CustomerBuyTicket {
	HttpSession session;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int buyTicket(String movie_name,int customer_num) {		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		Date currentTime = new Date( );
		String dTime = formatter.format ( currentTime );		
		CustomerBuyTicketMapper custService=new CustomerBuyTicketMapper();
		Movie2 movie=custService.selectMovie(movie_name);//null point
		System.out.println("영화객체:"+movie);
		System.out.println("고객번호:"+customer_num);
		
		String ticketOrder = customer_num + Integer.toString(movie.getMovie_num()) + dTime;
		
		//System.out.println(ticketOrder);
	TicketOrder to=new TicketOrder();
	to.setCustomerNum(customer_num);
	to.setMovieListNum(movie.getMovie_num());
	to.setTicketNum(ticketOrder);
	to.setTime(dTime);
	int res=custService.customerBuyTicket(to);
	return res;
		
	}
}
