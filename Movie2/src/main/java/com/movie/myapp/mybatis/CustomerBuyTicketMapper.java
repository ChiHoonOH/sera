package com.movie.myapp.mybatis;



import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.movie.myapp.model.Movie;
import com.movie.myapp.model.Movie2;
import com.movie.myapp.model.TicketOrder;


public class CustomerBuyTicketMapper {
	private static SqlSessionFactory sqlMapper;
	static {
		try {
			String resource = "SqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
		}
	}

	public  int customerBuyTicket(TicketOrder dto) {
		SqlSession session = sqlMapper.openSession();
		int res=session.insert("customerBuyTicket",dto);
		session.commit();
		session.close();		
		return res;
	}
	
	public  Movie2 selectMovie(String movie_name) {
		SqlSession session = sqlMapper.openSession();
		System.out.println("영화출력 이전:");
		Object obj=session.selectOne("selectMovie",movie_name);
		System.out.println("영화출력obj:"+obj);
		Movie2 list=(Movie2)obj;		
		System.out.println("영화출력:"+list);
		
		session.close();		
		return list;
	}
}