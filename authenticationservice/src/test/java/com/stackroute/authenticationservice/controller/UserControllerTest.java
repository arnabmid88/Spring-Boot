package com.stackroute.authenticationservice.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.repository.UserRepository;
import com.stackroute.authenticationservice.service.SecurityTokenGenerator;
import com.stackroute.authenticationservice.service.UserService;
import com.stackroute.authenticationservice.service.UserServiceImpl;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.event.spi.PostCollectionRecreateEvent;

import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	private User user;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private SecurityTokenGenerator securityTokenGenerator;
	
	@InjectMocks
	private UserController userController;
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		
		user = new User();
		
		user.setUsername("Arnab123");
		user.setPassword("arnab123");	
	}
	
	private String  jsonToString( final Object object) {
		
		String result;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonContent = objectMapper.writeValueAsString(object);
			result = jsonContent;
		}catch(JsonProcessingException jpse) {
			result = "JSON processing exception";
		}
		 
		 return result;
	}
	
	@Test
	public void testSaveUser() throws Exception {
		when(userService.saveUser(any())).thenReturn(user);
		when(userService.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
		
		mockMvc.perform(post("/api/v1/userservice/save").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user))).
			andExpect(status().isCreated()).andDo(print());
		
		verify(userService,times(1)).saveUser(any());
	}
	@Test
	public void testLoginSuccess() throws Exception {
		when(userService.saveUser(user)).thenReturn(user);
		when(userService.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
		
		mockMvc.perform(post("/api/v1/userservice/login").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user))).
			andExpect(status().isOk()).andDo(print());
		
		verify(userService,times(1)).findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}

}
