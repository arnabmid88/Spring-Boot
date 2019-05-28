package com.stackroute.authenticationservice.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.service.UserServiceImpl;
import com.stackroute.rabbitmq.domain.UserDTO;

@Component
public class Consumer {
	
	@Autowired
	private UserServiceImpl userService;

	@RabbitListener(queues="user_queue")
	public void getUserDtoFromRabbitmq(UserDTO userDto) {
		User user = new User();
		
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		
		userService.saveUser(user);
	}
}
