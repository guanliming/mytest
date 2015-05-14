package com.qianlong.filter;

import static com.qianlong.constants.SystemConstant.SESSION_LOGIN_NAME;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

	private static final List<String> ignoreURI = Arrays.asList("login");

	@Override
	public void destroy() {
		//
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest hsreq = (HttpServletRequest) request;
		final HttpServletResponse hsresp = (HttpServletResponse) response;
		final String name = hsreq.getParameter("name");
		final HttpSession session = hsreq.getSession();
		final String loginNameFromSession = String.valueOf(session.getAttribute(SESSION_LOGIN_NAME));
		if (!StringUtils.isBlank(loginNameFromSession) && StringUtils.equals(loginNameFromSession, name)) {
			chain.doFilter(request, response);
			return;
		}
		final String uri = hsreq.getRequestURI();
		for (final String eachURI : ignoreURI) {
			if (uri.indexOf(eachURI) != -1) {
				chain.doFilter(request, response);
				return;
			}
		}
//		hsresp.sendRedirect("/test-action/login");
		hsreq.getRequestDispatcher("/login").forward(request, response);
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		//
	}

}
