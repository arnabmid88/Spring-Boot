package com.stackroute.muzixservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.muzixservice.domain.Artist;
import com.stackroute.muzixservice.domain.Image;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.domain.User;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.service.MuzixServices;
import com.stackroute.muzixservice.service.UserTrackService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=UserTrackController.class)
public class UserTrackControllerTest  {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserTrackService userTrackService;
	
	private Track track;
	private Artist artist;
	private Image image;
	private User user;
	
	private List<Track> trackList = null;
	
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		image = new Image(1, "http:url", "large");
		artist = new Artist(123, "Hemanta", "new artist url", image);
		track = new Track("track234", "track name", "new comments", "123", "new track url", artist);
		
		trackList = new ArrayList<Track>();
		trackList.add(track);
		
		image = new Image(2, "http:url", "large");
		artist = new Artist(12, "Manna", "artist url", image);
		track = new Track("track2345", "track name", "new comments", "123", "new track url", artist);
		
		trackList.add(track);
		
		user = new User("Arnab", "arnab@gmail.com", trackList);
	}
	
	private String  jsonToString( final Object object) {
		
		String result;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonContent = objectMapper.writeValueAsString(object);
			result = jsonContent;
		}catch(JsonProcessingException jpse) {
			result = "JSON processing exception";
		}
		 
		 return result;
	}
	
	@After
	public void tearDown() {
		image = null;
		artist = null;
		track = null;
		user = null;
	}
	
	@Test
	public void testSaveTrackSuccess() throws Exception{
		when(userTrackService.saveUserTrackToWishlist(any(), eq(user.getUsername()))).thenReturn(user);
		
		mockMvc.perform(post("/api/v1/usertrackservice/user/{username}/track", user.getUsername()).contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isCreated()).andDo(print());
					
		verify(userTrackService,times(1)).saveUserTrackToWishlist(any(), eq(user.getUsername()));
	}
	
	@Test
	public void testSaveTrackFailure() throws Exception{
		when(userTrackService.saveUserTrackToWishlist(any(), eq(user.getUsername()))).thenThrow(TrackAlreadyExistsException.class);
		
		mockMvc.perform(post("/api/v1/usertrackservice/user/{username}/track", user.getUsername()).contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
		.andExpect(status().isConflict()).andDo(print());
					
		verify(userTrackService,times(1)).saveUserTrackToWishlist(any(), eq(user.getUsername()));
	}
	
	@Test
	public void testUpdateCommentSuccess() throws Exception{
		
		when(userTrackService.updateCommentForTrack(track.getComments(), track.getTrackId(), user.getUsername())).thenReturn(user);
		
		mockMvc.perform(patch("/api/v1/usertrackservice/user/{username}/track", user.getUsername()).contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isOk()).andDo(print());
					
		verify(userTrackService,times(1)).updateCommentForTrack(track.getComments(), track.getTrackId(), user.getUsername());
	}
	
	@Test
	public void testDeleteTrack() throws Exception{
		
		when(userTrackService.deleteUserTrackFromWishList(user.getUsername(), track.getTrackId())).thenReturn(user);
		
		mockMvc.perform(delete("/api/v1/usertrackservice/user/{username}/track", user.getUsername()).contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isOk()).andDo(print());
					
		verify(userTrackService,times(1)).deleteUserTrackFromWishList(user.getUsername(), track.getTrackId());
	}
	
	@Test
	public void testGetAllTrack() throws Exception{
		
		when(userTrackService.getAllUserTrackFromWishlist(user.getUsername())).thenReturn(trackList);
		
		mockMvc.perform(get("/api/v1/usertrackservice/user/{username}/tracks", user.getUsername()).contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isOk()).andDo(print());
					
		verify(userTrackService,times(1)).getAllUserTrackFromWishlist(user.getUsername());
	}

}
