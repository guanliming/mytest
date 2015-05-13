package com.qianlong.filter;

import static com.qianlong.constants.SystemConstant.SESSION_LOGIN_NAME;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
@Slf4j
public class LoginFilter implements Filter {
	@Override
	public void destroy() {
		//
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest hsreq = (HttpServletRequest) request;
//		 final HttpServletResponse hsresp = (HttpServletResponse) response;
		final String name = hsreq.getParameter("name");
		final HttpSession session = hsreq.getSession();
		final String loginNameFromSession = String.valueOf(session.getAttribute(SESSION_LOGIN_NAME));
		if (!StringUtils.isBlank(loginNameFromSession) && StringUtils.equals(loginNameFromSession, name)) {
			chain.doFilter(request, response);
			return;
		}
		hsreq.getRequestDispatcher("/index.html").forward(request, response);
		log.info("还没登陆啊！！");
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		//
	}

}
