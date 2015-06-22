package com.vcloudairshare.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class AdminFilter implements Filter {
	private FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.setFilterConfig(filterConfig);
	}

	public void destroy() {
		this.setFilterConfig(null);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException{
			doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
			
	}
	public void doFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//This will need to be fixed.
//		UserService userService = UserServiceFactory.getUserService();
//		if (userService.isUserLoggedIn() && userService.isUserAdmin()) {
			chain.doFilter(request, response);
//
//		} else {
//			response.sendRedirect(userService.createLoginURL(request.getRequestURL().toString()));
//		}
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}