package com.stackroute.orchestrationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stackroute.orchestrationservice.domain.User;
import com.stackroute.orchestrationservice.exception.UserAlreadyExistsException;
import com.stackroute.orchestrationservice.service.OrchestrationService;

@Controller
@RequestMapping("api/v1/")
@CrossOrigin("*")
public class OchestrationController {
	
	OrchestrationService orchestrationService;
	
	@Autowired
	OchestrationController(OrchestrationService orchestrationService){
		this.orchestrationService = orchestrationService;
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> registerAndSave(@RequestBody User user) throws UserAlreadyExistsException{
		ResponseEntity responseEntity = null;
		try {
			User userObj = this.orchestrationService.registerUser(user);
			responseEntity = new ResponseEntity(userObj, HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			throw new UserAlreadyExistsException();
		}
		return responseEntity;
	}

}
