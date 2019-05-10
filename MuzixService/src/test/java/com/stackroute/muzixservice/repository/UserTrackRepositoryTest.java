package com.stackroute.muzixservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.muzixservice.domain.Artist;
import com.stackroute.muzixservice.domain.Image;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.domain.User;

@DataMongoTest
@RunWith(SpringRunner.class)
public class UserTrackRepositoryTest {

	@Autowired
	private UserTrackRepository userTrackRepository;
	
	private Track track;
	private User user;
	
	@Before
	public void setup() {
		Image image = new Image(1, "http:url", "large");
		Artist artist = new Artist(123, "Hemanta", "new artist url", image);
		track = new Track("track234", "track name", "new comments", "123", "new track url", artist);
		
		List<Track> trackList = new ArrayList<Track>();
		trackList.add(track);
		user = new User("Arnab", "arnab@gmail.com", trackList);
	}
	
	@After
	public void tearDown() {
		
		userTrackRepository.deleteAll();
	}
	
	@Test
	public void testSaveUser() {
		userTrackRepository.save(user);
		User fetchUser = userTrackRepository.findByUserName(user.getUserName());
		Assert.assertEquals(fetchUser.getTrackList().get(0).getTrackId(),user.getTrackList().get(0).getTrackId());
	}
	
//	@Test
//	public void testUpdateComments() {
//		userTrackRepository.save(user);
//		User fetchUser = userTrackRepository.findByUserName(user.getUserName());
//		fetchUser.set("new comments Added");
//		muzixRepository.save(fetchTrack);
//		Track fetchTrackNew = muzixRepository.findById(track.getTrackId()).get();
//		
//		Assert.assertEquals("new comments Added",fetchTrackNew.getComments());
//	}
//	
//	@Test
//	public void testDeleteTrack() {
//		muzixRepository.insert(track);
//		Track fetchTrack = muzixRepository.findById(track.getTrackId()).get();
//		
//		muzixRepository.delete(fetchTrack);
//		Assert.assertEquals(Optional.empty(),muzixRepository.findById(track.getTrackId()));
//	}
//	
//	@Test
//	public void testGetAllTracks() {
//		muzixRepository.insert(track);
//		
//		image = new Image(2, "http:url", "large");
//		artist = new Artist(1234, "Manna", "artist url", image);
//		track = new Track("track1234", "my track", "additional comments", "1234", "new track url", artist);
//		muzixRepository.insert(track);
//		List<Track> trackList = muzixRepository.findAll();
//	
//		Assert.assertEquals(2,trackList.size());
//		Assert.assertEquals("track1234", trackList.get(1).getTrackId());
//	}
}
