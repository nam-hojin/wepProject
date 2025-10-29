package org.embed.Controll;


import org.embed.dto.UsDTO;
import org.embed.service.UsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class UsController {
	
	@Autowired
	private UsService usService;
	
	@RequestMapping("/main.to")
	public String main() {

		return "main";

	}
	
	@RequestMapping("/insertUs.to")
	public String insertUs() {
	   
	    return "insertUs";

	}
	
}
