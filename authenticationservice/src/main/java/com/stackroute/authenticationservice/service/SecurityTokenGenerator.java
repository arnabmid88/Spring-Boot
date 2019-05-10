package com.stackroute.authenticationservice.service;

import java.util.Map;

import com.stackroute.authenticationservice.domain.User;

public interface SecurityTokenGenerator {
	
	Map<String, String> generateToken(User user);
}
