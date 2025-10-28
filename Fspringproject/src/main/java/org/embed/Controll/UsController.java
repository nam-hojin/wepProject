package org.embed.Controll;


import org.embed.service.UsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	
}
