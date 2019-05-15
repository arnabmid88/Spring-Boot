package com.stackroute.authenticationservice.service;

import org.springframework.stereotype.Service;

import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	
	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) throws UserNotFoundException {
		System.out.println("username-->"+username+" password--->"+password);
		User user = userRepository.findByUsernameAndPassword(username, password);
		
		if(user == null) {
			throw new UserNotFoundException();
		}else {
			return user;
		}
	}

}
