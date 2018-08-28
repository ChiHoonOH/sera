package com.coderby.myapp.hr.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.validation.Valid;

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

import com.coderby.myapp.hr.model.EmpVO;
import com.coderby.myapp.hr.service.IEmpService;

@Controller
public class EmpController {

	@Autowired
	IEmpService empService;
	
	/*
	@RequestMapping(value="/hr/count")
	public String empCount(
		@RequestParam(value="deptid", required=false, defaultValue="0") int deptid, 
		Model model) {
		if(deptid==0) {
			model.addAttribute("count", empService.getEmpCount());
		}else {
			model.addAttribute("count", empService.getEmpCount(deptid));
		}
		return "hr/count";
	}
	*/
	
	/*
	@RequestMapping(value={"/hr", "/hr/list"})
	public String getAllEmps(Model model) {
		List<EmpVO> empList = empService.getEmpList();
		model.addAttribute("empList", empList);
		return "hr/list";
	}
	
	
	@RequestMapping(value="/hr/{employeeId}")
	public String getEmpInfo(@PathVariable int employeeId, Model model) {
		EmpVO emp = empService.getEmpInfo(employeeId);
		model.addAttribute("emp", emp);
		return "hr/view";
	}
	*/
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String form(Model model) {
		
	    return "form";
	}

	@RequestMapping(value="/result", method=RequestMethod.GET)
	public String result(String name,Model model) {
		
		//전달할 인자 값 정의 (문자열로 정의)
				String a = name;
				System.out.println(a);
				
				String body = "url=" + Base64.getEncoder().encodeToString(a.getBytes());

				String str="";
				try
				{
					//url 설정
					URL url = new URL("http://localhost:5000/article");
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					
					//리퀘스트 방식 정의 및 옵션 설정
					conn.setRequestMethod("POST");
					conn.setDoInput(true);
					conn.setDoOutput(true);
					
					//output 스트림
					OutputStream os = conn.getOutputStream();
					
					byte[] bytes = body.getBytes("UTF-8");
					
					os.write(bytes);
					os.flush();
					os.close();
					
					BufferedReader br = new BufferedReader( new InputStreamReader( conn.getInputStream(), "UTF-8" ), conn.getContentLength() );

					String buf;
					while( ( buf = br.readLine() ) != null ) 
					{
						//System.out.println( buf );
						str += buf;
					}
					//System.out.println("end-------------");
					br.close();
					/*
					InputStream is = conn.getInputStream();
					Scanner scan = new Scanner(is);
					
					while(scan.hasNext())
					{
						String str = scan.nextLine();
						System.out.println(str);				
					}
					*/ 
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				model.addAttribute("url", str);
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