package com.massurl.url;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MUErrorController implements ErrorController {
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        model.addAttribute("status", statusCode);
	        model.addAttribute("message", message);
	    }
	    return "error";
	}
}
