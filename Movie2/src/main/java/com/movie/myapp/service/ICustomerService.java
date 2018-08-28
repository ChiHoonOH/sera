package com.movie.myapp.service;

import java.util.List;

import com.movie.myapp.model.Customer;

import com.movie.myapp.model.Movie2;
import com.movie.myapp.model.TicketOrder;

public interface ICustomerService {
//회원가입	
public void CustomerInputData(Customer cust);
//티켓구매
/*public void CustomerBuyTicket(String movie_name);*/
//고객정보 출력
public int CustomerPrintData(String id,String pass);
//추천 순위
public List<Movie2> movieRecommend(int customer_num);
public List<TicketOrder> selectReservation();
public int customerBuyTicket(TicketOrder dto);
public Movie2 selectMovie(String movie_name);
public void analysisTicketOrder();

}
