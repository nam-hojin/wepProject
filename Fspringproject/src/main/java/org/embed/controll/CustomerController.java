package org.embed.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import org.springframework.ui.Model;

@Controller
public class CustomerController {

	@GetMapping("/customer.ko")
	public String customerPage() {

		return "customer/customer";
	}

	@GetMapping("/JuiceRecipes.ko")
	public String showJuiceRecipes() {

		return "content/JuiceRecipes";
	}

	@GetMapping("/Health.ko")
	public String showVitaminInfo() {

		return "content/Health";
	}

}
