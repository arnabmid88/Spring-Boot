package com.stackroute.authenticationservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.service.SecurityTokenGenerator;
import com.stackroute.authenticationservice.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/userservice")
public class UserController {
	
	private ResponseEntity responseEntity;
	
	private UserService userService;
	private SecurityTokenGenerator tokeGenerator;
	
	@Autowired
	public UserController(UserService userService, SecurityTokenGenerator tokeGenerator) {
		super();
		this.userService = userService;
		this.tokeGenerator = tokeGenerator;
	}
	
	@PostMapping("/save")
	public ResponseEntity saveUser(@RequestBody User user) {
		userService.saveUser(user);
		
		return responseEntity = new ResponseEntity(user, HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity loginUser(@RequestBody User user) throws UserNotFoundException {
		
		Map<String, String> map = null;
		try {
			User userObj = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
			
			if(userObj.getUsername().equals(user.getUsername())) {
				map = tokeGenerator.generateToken(user);
			}
			
			responseEntity = new ResponseEntity(map, HttpStatus.OK);
		}catch(UserNotFoundException unfe) {
			throw unfe;
		}catch(Exception e) {
			responseEntity = new ResponseEntity("Please try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

}
