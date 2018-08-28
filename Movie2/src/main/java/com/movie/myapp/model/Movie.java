package com.movie.myapp.model;

public class Movie {

	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public int getMovieNum() {
		return movieNum;
	}
	public void setMovieNum(int movieNum) {
		this.movieNum = movieNum;
	}
	
	
	private String movieName;
	private int movieNum;
	public Movie(int num, String name)
	{
		this.movieNum = num;
		this.movieName = name;
	}
	public Movie() {}
	
}
