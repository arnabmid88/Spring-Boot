package com.stackroute.muzixservice.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	
	@Id
	private String userName;
	private String email;
	private List<Track> trackList;
	
	
	
	public User() {
		super();
	}



	public User(String userName, String email, List<Track> trackList) {
		super();
		this.userName = userName;
		this.email = email;
		this.trackList = trackList;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
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
		return "User [userName=" + userName + ", email=" + email + ", trackList=" + trackList + "]";
	}
	
	
	
	

}
