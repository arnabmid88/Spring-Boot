package com.stackroute.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.authenticationservice.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByUsernameAndPassword(String username, String password);

}
