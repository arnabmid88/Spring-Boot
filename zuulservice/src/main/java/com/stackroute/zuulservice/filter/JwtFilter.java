package com.stackroute.zuulservice.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest servletRequest = (HttpServletRequest) request;
		final HttpServletResponse servletResponse = (HttpServletResponse) response;
		final String authHeader = servletRequest.getHeader("authorization");
		
		if("OPTIONS".equals(servletRequest.getMethod())) {
			servletResponse.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(servletRequest, servletResponse);
		}else {
			System.out.println("Auth Header-->"+authHeader);
			System.out.println("Authheader starts with-->"+authHeader.startsWith("Bearer "));
			System.out.println(authHeader == null || !authHeader.startsWith("Bearer "));
			if(authHeader == null || !authHeader.startsWith("Bearer ")) {
				System.out.println("Token not present");
				throw new ServletException("Missing or invalid authorization token!");
			}
			else {
				System.out.println("Token present");
				final String token = authHeader.substring(7);
				final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
				
				servletRequest.setAttribute("claims", claims);
				
				chain.doFilter(servletRequest, servletResponse);
			}
			
		}
		
		
	}

}
