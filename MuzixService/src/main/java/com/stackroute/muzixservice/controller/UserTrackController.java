package com.stackroute.muzixservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.domain.User;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;
import com.stackroute.muzixservice.exception.UserAlreadyExistsException;
import com.stackroute.muzixservice.service.UserTrackService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/usertrackservice")
public class UserTrackController {
	
	private UserTrackService userTrackService;
	private ResponseEntity responseEntity;
	
	@Autowired
	public UserTrackController(UserTrackService userTrackService) {
		super();
		this.userTrackService = userTrackService;
	}
	
	@PostMapping("/register")
	public ResponseEntity registerUser(@RequestBody User user) throws UserAlreadyExistsException {
		
		try {
			userTrackService.registerUser(user);
			responseEntity = new ResponseEntity<User>(user, HttpStatus.CREATED);
		}catch(UserAlreadyExistsException uaee) {
			throw new UserAlreadyExistsException();
		}
		return responseEntity;
	}
	
	@PostMapping("/user/{username}/track")
	public ResponseEntity<?> saveUserTrackToWishlist(@RequestBody Track track, @PathVariable("username") String username) throws TrackAlreadyExistsException{
		
		try {
			User user = userTrackService.saveUserTrackToWishlist(track, username);
			
			responseEntity = new ResponseEntity(user, HttpStatus.CREATED);
			
		}catch(TrackAlreadyExistsException taee) {
			throw new TrackAlreadyExistsException();
		}catch(Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@DeleteMapping("user/{username}/{trackId}")
	public ResponseEntity<?> deleteTrackFromWishlist(@PathVariable("username") String username, @PathVariable("trackId") String trackId) throws TrackNotFoundException {
		
		try {
			User user = userTrackService.deleteUserTrackFromWishList(username, trackId);
			responseEntity = new ResponseEntity(user, HttpStatus.OK);
		}catch(TrackNotFoundException tnfe) {
			throw new TrackNotFoundException();
		}catch(Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@PatchMapping("user/{username}/track")
	public ResponseEntity<?> updateCommentForUserTrack(@PathVariable("username") String username, @RequestBody Track track) throws TrackNotFoundException {
		
		try {
			userTrackService.updateCommentForTrack(track.getComments(), track.getTrackId(), username);
			responseEntity = new ResponseEntity(track, HttpStatus.OK);
		}catch(TrackNotFoundException tnfe) {
			throw new TrackNotFoundException();
		}catch(Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/user/{username}/tracks")
	public ResponseEntity<?> getAllTrackFromWishlist(@PathVariable("username") String username){
		
		try {
			responseEntity = new ResponseEntity(userTrackService.getAllUserTrackFromWishlist(username), HttpStatus.OK);
		}catch(Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	

}
