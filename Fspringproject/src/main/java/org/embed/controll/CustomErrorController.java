package org.embed.controll;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

		if (statusCode != null) {
			switch (statusCode) {
			case 404:
				return "error/404error";
			case 500:
				return "error/505error";
			default:
				return "error/505error";
			}
		}
		return "error/505error";
	}
}