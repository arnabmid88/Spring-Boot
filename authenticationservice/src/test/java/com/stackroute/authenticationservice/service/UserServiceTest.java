package com.stackroute.authenticationservice.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.repository.UserRepository;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	private User user;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Before
	public void setup() {
		user = new User();
		
		user.setUsername("Arnab123");
		user.setPassword("arnab123");
	}
	
	@After
	public void tearDown() {
		userRepository.deleteAll();
	}
	
	@Test
	public void testSaveUserSuccess() {
		
		Mockito.when(userRepository.save(user)).thenReturn(user);
		
		User fetchUser = userService.saveUser(user);
		Assert.assertEquals(user.getUsername(), fetchUser.getUsername());
		Mockito.verify(userRepository, Mockito.times(1)).save(user);
	}
	
	@Test
	public void testFindbyUsernameandPassword() throws UserNotFoundException {
		
		Mockito.when(userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword())).thenReturn(user);
		
		User fetchUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		Assert.assertEquals(user.getUsername(), fetchUser.getUsername());
		Mockito.verify(userRepository, Mockito.times(1)).findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}

}
