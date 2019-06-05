package tpm.qlts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("home")
public class HomeController {

	@RequestMapping(value = "conmeo", method = RequestMethod.GET)
	public String hello() {
		return "Xin chà";
	}

}
