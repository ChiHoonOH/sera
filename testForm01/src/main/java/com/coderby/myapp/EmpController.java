package com.coderby.myapp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmpController {
	
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String form(Model model) {
		
	
	    return "hr/form";
	}

	@RequestMapping(value="/hr/form", method=RequestMethod.POST)
	public String insertEmp(){
		
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
	
	/*
	
	@RequestMapping(value="/hr/update", method=RequestMethod.GET)
	public String updateEmp(int empid, Model model) {
		model.addAttribute("emp", empService.getEmpInfo(empid));
		model.addAttribute("deptList", empService.getAllDeptId());
		model.addAttribute("jobList", empService.getAllJobId());
		model.addAttribute("managerList", empService.getAllManagerId());
		return "hr/updateform";
	}
	
	@RequestMapping(value="/hr/update", method=RequestMethod.POST)
	public String updateEmp(EmpVO emp, Model model) {
		empService.updateEmp(emp);
		return "redirect:/hr/"+emp.getEmployeeId();
	}
	
	@RequestMapping(value="/hr/delete", method=RequestMethod.GET)
	public String deleteEmp(int empid, Model model) {
		model.addAttribute("emp", empService.getEmpInfo(empid));
		return "hr/deleteform";
	}

	@RequestMapping(value="/hr/delete", method=RequestMethod.POST)
	public String deleteEmp(int empid, String email, Model model) {
		empService.deleteEmp(empid, email);
		return "redirect:/hr";
	}
	*/
	
}//end class