package com.movie.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.movie.myapp.dao.*;
import com.movie.myapp.model.Customer;
import com.movie.myapp.model.Movie2;
import com.movie.myapp.model.TicketOrder;
import com.movie.myapp.mybatis.CustomerBuyTicketMapper;

public class CustomerService implements ICustomerService{
	
	
	
	@Autowired
	CustomerInputData customerInputData; 
	/*@Autowired
	CustomerBuyTicket customerBuyTicket;*/
	@Autowired
	CustomerPrintData customerPrintData;
	@Autowired
	MovieRecommend movieRecommend;
	@Autowired
	SelectReservation selectReservation;
	@Autowired
	CustomerBuyTicketMapper customerBuyTicket;	
	@Autowired
	AnalysisTicketOrder analysisTicketOrder;
	
	
	@Override
	public void CustomerInputData(Customer cust) {	
		customerInputData.insertCustomerData(cust);
	}


	/*@Override
	public void CustomerBuyTicket(String movie_name) {
		customerBuyTicket.b(movie_name);	
	}*/


	@Override
	public int CustomerPrintData(String id, String pass) {
		int result=customerPrintData.printCustomerData(id, pass);
		return result;
	}


	@Override
	public List<Movie2> movieRecommend(int customer_num) {
		List<Movie2> result=movieRecommend.movieRecommend(customer_num);
		return result;
	}



	@Override
	public int customerBuyTicket(TicketOrder dto) {
		System.out.println(1);
		int res=customerBuyTicket.customerBuyTicket(dto);
		System.out.println(2);
		
		return res;
	}


	@Override
	public Movie2 selectMovie(String movie_name) {
		Movie2 list=customerBuyTicket.selectMovie(movie_name);
		return list;
	}


	@Override
	public List<TicketOrder> selectReservation() {
		List<TicketOrder> list=selectReservation.selectReservation(); 
		return list;
	}


	@Override
	public void analysisTicketOrder() {
		analysisTicketOrder.run();
	}
	
	
	
	

	
		
}