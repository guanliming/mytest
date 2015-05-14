package com.qianlong.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public ModelAndView gotoLoginPage(){
		return new ModelAndView("login");
//		return new ModelAndView("forward:/WEB-INF/views/login.jsp");
	}
	
	

}
