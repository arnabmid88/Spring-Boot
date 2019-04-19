package com.stackroute.muzixservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.stackroute.muzixservice.domain.Artist;
import com.stackroute.muzixservice.domain.Image;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;
import com.stackroute.muzixservice.repository.MuzixRepository;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MuzixServiceTest {
	
	@Mock
	private MuzixRepository muzixRepository;
	
	private Track track;
	private Artist artist;
	private Image image;
	private Optional optional;
	
	private List<Track> trackList = null;
	
	@InjectMocks
	private MuzixServicesImpl muzixServicesImpl;
	
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		image = new Image(1, "http:url", "large");
		artist = new Artist(123, "Hemanta", "new artist url", image);
		track = new Track("track234", "track name", "new comments", "123", "new track url", artist);
		
		trackList = new ArrayList<Track>();
		trackList.add(track);
		
		optional = Optional.of(track);
	}
	
	@After
	public void tearDown() {
		image = null;
		artist = null;
		track = null;
		muzixRepository.deleteAll();
	}
	
	@Test
	public void testSaveTrackSuccess() throws TrackAlreadyExistsException{
		when(muzixRepository.insert(track)).thenReturn(track);
		
		Track fetchTrack = muzixServicesImpl.saveTrackToWishList(track);
		Assert.assertEquals(track, fetchTrack);
		
		verify(muzixRepository,times(1)).insert(track);
		verify(muzixRepository,times(1)).findById(track.getTrackId());
	}
	
	@Test(expected=TrackAlreadyExistsException.class)
	public void testSaveTrackFailure() throws TrackAlreadyExistsException{
		when(muzixRepository.insert(track)).thenReturn(track);
		when(muzixRepository.findById(track.getTrackId())).thenReturn(optional);
		Track fetchTrack = muzixServicesImpl.saveTrackToWishList(track);
		Assert.assertEquals(track, fetchTrack);
		
		Mockito.verify(muzixRepository,Mockito.times(1)).insert(track);
		Mockito.verify(muzixRepository,Mockito.times(1)).findById(track.getTrackId());
	}
	
	@Test
	public void testUpdateCommentSuccess() throws TrackNotFoundException{
		
		when(muzixRepository.findById(track.getTrackId())).thenReturn(optional);
		track.setComments("Comment Updated!");
		Track fetchTrack = muzixServicesImpl.updateCommentForTrack(track.getComments(), track.getTrackId());
		Assert.assertEquals(fetchTrack.getComments(), "Comment Updated!");
		
		Mockito.verify(muzixRepository,Mockito.times(1)).save(track);
		Mockito.verify(muzixRepository,Mockito.times(2)).findById(track.getTrackId());
	}
	
	@Test
	public void testDeleteTrack() throws TrackNotFoundException{
		
		when(muzixRepository.findById(track.getTrackId())).thenReturn(optional);
		
		boolean fetchTrack = muzixServicesImpl.deleteTrackFromWishList(track.getTrackId());
		Assert.assertEquals(true, fetchTrack);
		
		Mockito.verify(muzixRepository,Mockito.times(1)).findById(track.getTrackId());
		Mockito.verify(muzixRepository,Mockito.times(1)).deleteById(track.getTrackId());
	}
	
	@Test
	public void testGetAllTrack() throws Exception{
		
		when(muzixRepository.findAll()).thenReturn(trackList);
		
		List<Track> fetchTrack = muzixServicesImpl.getAllTracksFromWishlist();
		Assert.assertEquals(trackList, fetchTrack);
		
		Mockito.verify(muzixRepository,Mockito.times(1)).findAll();
	}

}
