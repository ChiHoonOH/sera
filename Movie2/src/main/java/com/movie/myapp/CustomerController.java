package com.movie.myapp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.movie.myapp.dao.CustomerBuyTicket;
import com.movie.myapp.model.Customer;
import com.movie.myapp.model.Movie2;
import com.movie.myapp.service.ICustomerService;

@Controller
public class CustomerController {

	@Autowired
	ICustomerService custService;
	HttpSession session;
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model) {		
		return "home";
	}
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main(Model model) {		
		return "main";
	}
	
	@RequestMapping(value="/message", method=RequestMethod.GET)
	public String message(Model model) {
		
		return "message";
	}
	/*@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView loginEmp(Customer cus, Model model,HttpServletRequest arg0) {
	ModelAndView mav=new ModelAndView();
	
	session=arg0.getSession();
	
	int cust_num=custService.CustomerPrintData(cus.getId(), cus.getPass());
	//System.out.println(cust_num);
	if(cust_num==0) {
		
	mav.addObject("msg","로그인을 실패하였습니다.");
	mav.addObject("url","/movie/");
	mav.setViewName("message");
	return mav;
	}else {session.setAttribute("no",cust_num);
	System.out.println("고객번호:"+session.getAttribute("no"));
	
	mav.setViewName("redirect:main");}	
	return mav;
	}	*/
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginEmp(Customer cus, Model model,HttpServletRequest arg0) {	
	session=arg0.getSession();
	
	int cust_num=custService.CustomerPrintData(cus.getId(), cus.getPass());
	//System.out.println(cust_num);
	if(cust_num==0) {
	model.addAttribute("msg","로그인을 실패하였습니다.");
	model.addAttribute("url","/movie/");	
	return "message";
	}else {session.setAttribute("no",cust_num);
	/*System.out.println("고객번호:"+session.getAttribute("no"));*/	
	return "redirect:main";	}
	
	}	
	
	/*@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginEmp(Customer cus, Model model,HttpServletRequest arg0) {
	
	
	session=arg0.getSession();
	
	int cust_num=custService.CustomerPrintData(cus.getId(), cus.getPass());
	//System.out.println(cust_num);
	if(cust_num==0) {
		String aa = "login failed";
	model.addAttribute("msg",aa);
	model.addAttribute("url","home");
	return "redirect:message";
	}else {session.setAttribute("no", cust_num);}	
	return "redirect:main";
	}	*/

	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insertEmp(Model model) {
		
		return "insertMember";
	}
	
	@RequestMapping(value="/insert_Member", method=RequestMethod.POST)
	public String insertEmp(Customer cus, Model model) {
		custService.CustomerInputData(cus);
		int cust_num=custService.CustomerPrintData(cus.getId(), cus.getPass());
		session.setAttribute("no",cust_num);
		return "redirect:main";
	}
	
	@RequestMapping(value="/reservation", method=RequestMethod.POST)
	public String reserveEmp(Customer emp, Model model) {
		
		return "reservation";
	}
	
	@RequestMapping(value="/reservation_Movie", method=RequestMethod.POST)
	public String reserve_MovieEmp(String movie_name, Model model,HttpServletRequest arg0) {
		System.out.println("영화이름:"+movie_name);
		CustomerBuyTicket cbt=new CustomerBuyTicket();
		session=arg0.getSession();
		int no=(Integer)session.getAttribute("no");
		System.out.println(no);
		cbt.buyTicket(movie_name,no);
		return "redirect:main";
	}
	
	@RequestMapping(value="/movie_recommandation", method=RequestMethod.POST)
	public String movie_recommandation(HttpServletRequest arg0, Model model) {	
		session=arg0.getSession();	
		custService.analysisTicketOrder();
		List<Movie2> result=null;
		int customer_num=(Integer)session.getAttribute("no");
		/*System.out.println(customer_num);*/
		result=custService.movieRecommend(customer_num);			
		model.addAttribute("result",result);
		return "movieRecommendation";
	}
	
	/*@RequestMapping(value="/reservation_history", method=RequestMethod.POST)
	public String reservation_history(Model model) {
		List list=custService.SelectReservation();		
		model.addAttribute("result", list);		
		return "selectReservation";
	}*/
	@RequestMapping(value="/select_schedule", method=RequestMethod.POST)
	public String select_Schedule(Customer cust,String movie_name, Model model) {
		
		return "selectSchedule";
	}
	
	@RequestMapping(value="/select_reservation", method=RequestMethod.POST)
	public ModelAndView select_reservation(Customer cust,String movie_name, Model model) {
		ModelAndView mav=new ModelAndView();
		List list=null;
		System.out.println("이전"+list);
		list=custService.selectReservation();
		System.out.println("이후"+list);
		mav.addObject("result", list);
		mav.setViewName("selectReservation");		
		return mav;
	}
	
	
	
	
	
}//end class