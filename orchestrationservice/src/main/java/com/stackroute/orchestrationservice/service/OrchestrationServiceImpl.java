package com.stackroute.orchestrationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stackroute.orchestrationservice.domain.User;
import com.stackroute.orchestrationservice.exception.UserAlreadyExistsException;

@Service
public class OrchestrationServiceImpl implements OrchestrationService {

	@Autowired
	RestTemplate restTemplate;
	
	String urlUserTrackService = "http://muzixservice/api/v1/usertrackservice/register";
	String urlAuthenticationService = "http://authenticationservice/api/v1/userservice/save";
	
	@Override
	public User registerUser(User user) throws UserAlreadyExistsException {
		User userResponse;
		try {
			userResponse = restTemplate.postForObject(urlUserTrackService, user, User.class);
			restTemplate.postForObject(urlAuthenticationService, user, User.class);
		}catch (Exception e) {
			throw new UserAlreadyExistsException();
		}
		return userResponse;
	}

}
