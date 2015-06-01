package com.qianlong.filter;

import static com.qianlong.biz.constants.SystemConstant.SESSION_LOGIN_NAME;

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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
public class LoginFilter implements Filter {

	private static final List<String> ignoreURI = Arrays.asList("register",".validate","signin","http");

	@Override
	public void destroy() {
		//
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest hsreq = (HttpServletRequest) request;
		final HttpSession session = hsreq.getSession();
		//判断是否已经登陆
		final Object loginNameFromSession = session.getAttribute(SESSION_LOGIN_NAME);
		if (loginNameFromSession!=null &&!StringUtils.isBlank(String.valueOf(loginNameFromSession)) ) {
			chain.doFilter(request, response);
			return;
		}
		//判断是否是非验证登陆url
		final String uri = hsreq.getRequestURI();
		for (final String eachURI : ignoreURI) {
			if (uri.indexOf(eachURI) != -1) {
				chain.doFilter(request, response);
				return;
			}
		}
		// hsresp.sendRedirect("/test-action/login");
		// hsreq.getRequestDispatcher("/registerPage").forward(request,
		// response);
		//需验证的url但却没登陆，转向登陆页面
		hsreq.getRequestDispatcher("/login").forward(request, response);
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		//
	}

}
