package com.stackroute.muzixservice.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.muzixservice.domain.Artist;
import com.stackroute.muzixservice.domain.Image;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.domain.User;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;
import com.stackroute.muzixservice.repository.MuzixRepository;
import com.stackroute.muzixservice.repository.UserTrackRepository;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class UserTrackServiceTest {

	@Mock
	private UserTrackRepository userTrackRepository;
	
	private Track track;
	private Artist artist;
	private Image image;
	private Optional optional;
	private User user;
	
	private List<Track> trackList = null;
	
	@InjectMocks
	private UserTrackServiceImpl userTrackServicesImpl;
	
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		image = new Image(1, "http:url", "large");
		artist = new Artist(123, "Hemanta", "new artist url", image);
		track = new Track("track234", "track name", "new comments", "123", "new track url", artist);
		
		trackList = new ArrayList<Track>();
		trackList.add(track);
		user = new User("Arnab", "arnab@gmail.com", trackList);
	}
	
	@After
	public void tearDown() {
		image = null;
		artist = null;
		track = null;
		user = null;
		userTrackRepository.deleteAll();
	}
	
	@Test
	public void testSaveUserTrackSuccess() throws TrackAlreadyExistsException{
		user = new User("Arnab", "arnab@gmail.com", null);
		when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
		
		User fetchUser = userTrackServicesImpl.saveUserTrackToWishlist(track, user.getUserName());
		Assert.assertEquals(user, fetchUser);
		
		verify(userTrackRepository,times(1)).findByUserName(any());
		verify(userTrackRepository,times(1)).save(user);
	}
	
	@Test
	public void testUpdateCommentSuccess() throws TrackNotFoundException{
		
		when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
		User fetchUser = userTrackServicesImpl.updateCommentForTrack("Comment Updated!", track.getTrackId(), user.getUserName());
		
		Assert.assertEquals(fetchUser.getTrackList().get(0).getComments(), "Comment Updated!");
		
		Mockito.verify(userTrackRepository,Mockito.times(1)).findByUserName(user.getUserName());
		Mockito.verify(userTrackRepository,Mockito.times(1)).save(user);
	}
	
	@Test
	public void testDeleteUserTrack() throws TrackNotFoundException{
		
		when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
		
		User fetchUser = userTrackServicesImpl.deleteUserTrackFromWishList(user.getUserName(), track.getTrackId());
		Assert.assertEquals(fetchUser, user);
		
		Mockito.verify(userTrackRepository,Mockito.times(1)).findByUserName(user.getUserName());
		Mockito.verify(userTrackRepository,Mockito.times(1)).save(user);
	}
	
	@Test
	public void testGetAllUserTrack() throws Exception{
		
		when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
		
		List<Track> fetchTrackList = userTrackServicesImpl.getAllUserTrackFromWishlist(user.getUserName());
		Assert.assertEquals(trackList, fetchTrackList);
		
		Mockito.verify(userTrackRepository,Mockito.times(1)).findByUserName(user.getUserName());
	}
}
