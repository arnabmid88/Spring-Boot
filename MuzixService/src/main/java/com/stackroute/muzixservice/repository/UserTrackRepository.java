package com.stackroute.muzixservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.muzixservice.domain.User;

public interface UserTrackRepository extends MongoRepository<User, String>{
	
	public User findByUsername(String username);

}
