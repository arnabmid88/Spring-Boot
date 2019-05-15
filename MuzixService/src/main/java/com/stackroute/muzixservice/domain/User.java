package com.stackroute.muzixservice.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	
	@Id
	private String username;
	private String email;
	private List<Track> trackList;
	
	
	
	public User() {
		super();
	}



	public User(String username, String email, List<Track> trackList) {
		super();
		this.username = username;
		this.email = email;
		this.trackList = trackList;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public List<Track> getTrackList() {
		return trackList;
	}



	public void setTrackList(List<Track> trackList) {
		this.trackList = trackList;
	}



	@Override
	public String toString() {
		return "User [userName=" + username + ", email=" + email + ", trackList=" + trackList + "]";
	}
	
	
	
	

}
