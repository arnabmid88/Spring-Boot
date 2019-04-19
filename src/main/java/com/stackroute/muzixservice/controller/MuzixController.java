package com.stackroute.muzixservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;
import com.stackroute.muzixservice.service.MuzixServices;

@RestController
@RequestMapping("api/v1/muzixservice")
public class MuzixController {
	
	private MuzixServices muzixService;
	private ResponseEntity responseEntity;
	
	@Autowired
	public MuzixController(MuzixServices muzixServices) {
		this.muzixService = muzixServices;
	}
	
	@PostMapping("track")
	@ExceptionHandler(TrackAlreadyExistsException.class)
	public ResponseEntity<?> saveTrackToWishList(@RequestBody Track track) throws TrackAlreadyExistsException
	{
		try {
			muzixService.saveTrackToWishList(track);
			responseEntity = new ResponseEntity(track, HttpStatus.CREATED);
		}catch(TrackAlreadyExistsException taee) {
			throw new TrackAlreadyExistsException();
		}catch(Exception e) {
			responseEntity = new ResponseEntity("Error!!! Try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@DeleteMapping("track/{id}")
	@ExceptionHandler(TrackNotFoundException.class)
	public ResponseEntity<?> deleteTrackFromWishList(@PathVariable("id") String id) throws TrackNotFoundException
	{
		try {
			muzixService.deleteTrackFromWishList(id);
			responseEntity = new ResponseEntity("Succesfully deleted!", HttpStatus.OK);
		}catch(TrackNotFoundException tnfe) {
			throw new TrackNotFoundException();
		}catch(Exception e) {
			responseEntity = new ResponseEntity("Error!!! Try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@PutMapping("track/{id}")
	public ResponseEntity<?> updateCommentForTrack(@RequestBody Track track, @PathVariable("id") String id) throws TrackNotFoundException
	{
		try {
			muzixService.updateCommentForTrack(track.getComments(), id);
			responseEntity = new ResponseEntity(track, HttpStatus.OK);
		}catch(TrackNotFoundException tnfe) {
			throw new TrackNotFoundException();
		}catch(Exception e) {
			responseEntity = new ResponseEntity("Error!!! Try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("tracks")
	public ResponseEntity<?> getAllTracksFromWishList()
	{
		try {
			
			responseEntity = new ResponseEntity(muzixService.getAllTracksFromWishlist(), HttpStatus.OK);
		}catch(Exception e) {
			responseEntity = new ResponseEntity("Error!!! Try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}

}
