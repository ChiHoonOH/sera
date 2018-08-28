package backup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.movie.myapp.model.Customer;
@Controller
public class MainController {
	
	
	@RequestMapping(value="/input_schedule", method=RequestMethod.GET)
	public String input_Schedule(Customer emp, Model model) {
		
		return "reservation";
	}
	
	@RequestMapping(value="/select_schedule", method=RequestMethod.GET)
	public String select_Schedule(Customer emp, Model model) {
		
		return "reservation";
	}
	
	@RequestMapping(value="/reservation", method=RequestMethod.GET)
	public String reserveEmp(Customer emp, Model model) {
		
		return "reservation";
	}
	
	@RequestMapping(value="/select_reservation", method=RequestMethod.GET)
	public String select_Reservation(Customer emp, Model model) {
		
		return "reservation";
	}
	

}
