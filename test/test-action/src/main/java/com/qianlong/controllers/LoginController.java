package com.qianlong.controllers;

import static com.qianlong.constants.SystemConstant.SESSION_LOGIN_NAME;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@RequestMapping("/account.validate")
	public @ResponseBody String accountValidate(final HttpServletRequest request, final Model model) {
		if (userBiz.query(request.getParameter("account")) == null) {
			return "true";
		}
		return "false";
	}

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
		final UserEntry user = new UserEntry();
		user.setName(param.getUsername());
		user.setPassword(param.getPassword());
		userBiz.insert(user);
		return mv;
	}

	@RequestMapping("/registerPage")
	public ModelAndView registerPage() {
		return new ModelAndView("registerPage");
	}

	@RequestMapping("/signin")
	public ModelAndView signin(final HttpServletRequest request, final HttpSession session) {
		String username = request.getParameter("username");
		UserEntry user = userBiz.query(username);
		if (user != null && StringUtils.equals(user.getPassword(), request.getParameter("password"))) {
			session.setAttribute(SESSION_LOGIN_NAME, username);
			return new ModelAndView("main");
		}
		return signInPage();
	}

	@RequestMapping("/signinPage")
	public ModelAndView signInPage() {
		return new ModelAndView("signinPage");

	}

	@RequestMapping("/signout")
	public ModelAndView signOut(final HttpSession session) {
		// session.removeAttribute(SESSION_LOGIN_NAME);
		session.invalidate();
		return indexPage();
	}

}
