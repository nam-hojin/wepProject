package org.embed.Controll;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsController {

	@GetMapping("/main.to")
	public String main() {

		return "main";

	}

	@GetMapping("/insertUs.to")
	public String addmember() {
		
		
	return "insertUs";
	}
}
