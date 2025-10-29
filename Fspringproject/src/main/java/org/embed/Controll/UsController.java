package org.embed.Controll;

import org.embed.dto.UsDTO;
import org.embed.service.UsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UsController {

	@Autowired
	private UsService usService;

	@RequestMapping("/main.to")
	public String main() {

		return "/main";

	}

	@RequestMapping("/addUI.to")
	public String addUI() {

		return "/addUs";

	}

	
	 @RequestMapping("/insertUs.to") 
	 public String insertUs (@ModelAttribute UsDTO user) throws Exception { 
	  usService.insertUs(user);
	 	return "redirect:/main.to"; 
	  }
	 
	 @RequestMapping("/login.to")
	 public String login() {
		 
		 return "/login";
	 }
	 @RequestMapping("/loginProsses.to")
	 public String loginProsses() {
		 
		 return "";
		 
	 }
	 
	 
}
