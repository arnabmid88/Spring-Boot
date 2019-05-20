package com.stackroute.zuulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.stackroute.zuulservice.filter.JwtFilter;


@EnableZuulProxy
@SpringBootApplication
public class ZuulserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulserviceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		
		registrationBean.setFilter(new JwtFilter());
		
		registrationBean.addUrlPatterns("/muzixservice/api/v1/usertrackservice/user/*");
		
		return registrationBean;
	}
}
