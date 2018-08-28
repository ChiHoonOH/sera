package com.movie.myapp.model;

public class TicketOrder {
	
	private String ticketNum;
	private int customerNum;
	private int movieListNum;
	private String time;
	
	public TicketOrder() {}
	public TicketOrder(String ticketNum, int customerNum, int movieListNum, String time)
	{
		this.ticketNum = ticketNum;
		this.customerNum = customerNum;
		this.movieListNum = movieListNum;
		this.time = time;
		
	}
	
	public String getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}
	public int getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(int customerNum) {
		this.customerNum = customerNum;
	}
	public int getMovieListNum() {
		return movieListNum;
	}
	public void setMovieListNum(int movieListNum) {
		this.movieListNum = movieListNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
}
