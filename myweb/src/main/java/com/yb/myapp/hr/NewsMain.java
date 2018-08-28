package com.yb.myapp.hr;

import org.springframework.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.context.support.GenericWebApplicationContext;

public class NewsMain {

	public static void main(String[] args) {
		AbstractRefreshableWebApplicationContext context =
				new GenericWebApplicationContext("spring/application-config.xml");
			
			

	}

}
