package com.qianlong.controllers;

import static com.qianlong.constants.SystemConstant.SESSION_LOGIN_NAME;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qianlong.RegisterParamBo;
import com.qianlong.UserEntry;
import com.qianlong.biz.IUserBiz;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
@Controller
public class LoginController {
	@Autowired
	private IUserBiz userBiz;

	@RequestMapping("/login")
	public ModelAndView indexPage() {
		return new ModelAndView("index");
		// return new ModelAndView("forward:/WEB-INF/views/index.jsp");
	}

	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute final RegisterParamBo param, final HttpSession session) {
		final ModelAndView mv = new ModelAndView("main");
		// mv.addObject("username", param.getUsername());
		session.setAttribute(SESSION_LOGIN_NAME, param.getUsername());
		final UserEntry user=new UserEntry();
		user.setName(param.getUsername());
		userBiz.insert(user);
		return mv;
	}

	@RequestMapping("/registerPage")
	public ModelAndView registerPage() {
		return new ModelAndView("registerPage");
	}

	@RequestMapping("/signout")
	public ModelAndView signOut(final HttpSession session) {
		// session.removeAttribute(SESSION_LOGIN_NAME);
		session.invalidate();
		return indexPage();
	}

}
