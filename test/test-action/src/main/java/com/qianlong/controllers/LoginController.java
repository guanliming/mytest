package com.qianlong.controllers;

import static com.qianlong.biz.constants.SystemConstant.SESSION_LOGIN_NAME;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qianlong.RegisterParamBo;
import com.qianlong.UserEntity;
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

	@RequestMapping("/")
	public ModelAndView defaultRequest() {
		return indexPage();
	}

	@RequestMapping("/login")
	public ModelAndView indexPage() {
		return new ModelAndView("index");
		// return new ModelAndView("forward:/WEB-INF/views/index.jsp");
	}
	
	@RequestMapping("/main")
	public ModelAndView main(){
		return new ModelAndView("main");
	}

	@RequestMapping("/register")
	public ModelAndView register( final RegisterParamBo param, final HttpSession session) {
		if(!validateBeforeRegister(param)){
			return indexPage();
		}
		final ModelAndView mv = new ModelAndView("main");
		// mv.addObject("username", param.getUsername());
		session.setAttribute(SESSION_LOGIN_NAME, param.getUsername());
		final UserEntity user = new UserEntity();
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
		final String username = request.getParameter("username");
		final UserEntity user = userBiz.query(username);
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

	private boolean validateBeforeRegister(final RegisterParamBo param){
		if(StringUtils.isBlank(param.getUsername())||StringUtils.isBlank(param.getPassword())){
			return false;
		}
		return true;
	}

}
