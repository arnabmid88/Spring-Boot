package com.stackroute.muzixservice.controller;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.muzixservice.domain.Artist;
import com.stackroute.muzixservice.domain.Image;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;
import com.stackroute.muzixservice.repository.MuzixRepository;
import com.stackroute.muzixservice.service.MuzixServices;
import com.stackroute.muzixservice.service.MuzixServicesImpl;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=MuzixController.class)
public class MuzixControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MuzixServices muzixService;
	
	private Track track;
	private Artist artist;
	private Image image;
	private Optional optional;
	
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
		
		optional = Optional.of(track);
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
	}
	
	@Test
	public void testSaveTrackSuccess() throws Exception{
		when(muzixService.saveTrackToWishList(any())).thenReturn(track);
		
		mockMvc.perform(post("/api/v1/muzixservice/track").contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isCreated()).andDo(print());
					
		verify(muzixService,times(1)).saveTrackToWishList(any());
	}
	
	@Test
	public void testSaveTrackFailure() throws Exception{
		when(muzixService.saveTrackToWishList(any())).thenThrow(TrackAlreadyExistsException.class);
		
		mockMvc.perform(post("/api/v1/muzixservice/track").contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isConflict()).andDo(print());
					
		verify(muzixService,times(1)).saveTrackToWishList(any());
	}
	
	@Test
	public void testUpdateCommentSuccess() throws Exception{
		
		when(muzixService.updateCommentForTrack(track.getComments(), track.getTrackId())).thenReturn(track);
		
		mockMvc.perform(put("/api/v1/muzixservice/track/track2345").contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isOk()).andDo(print());
					
		verify(muzixService,times(1)).updateCommentForTrack(track.getComments(), track.getTrackId());
	}
	
	@Test
	public void testDeleteTrack() throws Exception{
		
		when(muzixService.updateCommentForTrack(track.getComments(), track.getTrackId())).thenReturn(track);
		
		mockMvc.perform(delete("/api/v1/muzixservice/track/track2345").contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isOk()).andDo(print());
					
		verify(muzixService,times(1)).deleteTrackFromWishList(track.getTrackId());
	}
	
	@Test
	public void testGetAllTrack() throws Exception{
		
		when(muzixService.getAllTracksFromWishlist()).thenReturn(trackList);
		
		mockMvc.perform(get("/api/v1/muzixservice/tracks").contentType(MediaType.APPLICATION_JSON).content(jsonToString(track)))
					.andExpect(status().isOk()).andDo(print());
					
		verify(muzixService,times(1)).getAllTracksFromWishlist();
	}
}
