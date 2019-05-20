//package com.stackroute.muzixservice.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.stackroute.muzixservice.filter.JwtFilter;
//
//@Configuration
//public class BeanConfiguration {
//
//	@Bean
//	public FilterRegistrationBean jwtFilter() {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		
//		registrationBean.setFilter(new JwtFilter());
//		
//		registrationBean.addUrlPatterns("/api/v1/usertrackservice/user/*");
//		
//		return registrationBean;
//	}
//}
