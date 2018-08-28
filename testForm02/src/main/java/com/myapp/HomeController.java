package com.myapp;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
		
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String form(Model model) {
		
	
	    return "hr/form";
	}

	@RequestMapping(value="/hr/form", method=RequestMethod.POST)
	public String insertEmp() {
		
		return "redirect:/hr";
	}
	
	@RequestMapping(value="/result", method=RequestMethod.GET)
	public String result(String name,Model model) {
		
		String str ="";

		try
		{
			//url 설정
			URL url = new URL("http://localhost:5000/");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
		
			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);
			
			while(scan.hasNext())
			{
				str += scan.nextLine();
				//System.out.println(str);				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		model.addAttribute("url",str);
	
	    
	    return "result";
	}
	
	
}
