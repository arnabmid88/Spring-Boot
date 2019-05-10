package com.stackroute.authenticationservice.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.authenticationservice.domain.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	private User user;
	
	@Before
	public void setup() {
		user = new User();
		
		user.setUserName("Arnab123");
		user.setPassword("arnab123");
	}
	
	@After
	public void tearDown() {
		userRepository.deleteAll();
	}
	
	@Test
	public void testSaveUserSuccess() {
		userRepository.save(user);
		User userobj = userRepository.findById(user.getUserId()).get();
		Assert.assertEquals(user.getUserName(), userobj.getUserName());
		userRepository.delete(user);
	}
	
	@Test
	public void testUserLoginSuccess() {
		userRepository.save(user);
		User userobj = userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
		Assert.assertEquals(user.getUserName(), userobj.getUserName());
		userRepository.delete(user);
	}

}
