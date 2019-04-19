package com.stackroute.muzixservice.repository;

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

@RunWith(SpringRunner.class)
@DataMongoTest
public class MuzixRepositoryTest {

	@Autowired
	private MuzixRepository muzixRepository;
	
	private Image image;
	private Artist artist;
	private Track track;
	
	@Before
	public void setup() {
		image = new Image(1, "http:url", "large");
		artist = new Artist(123, "Hemanta", "new artist url", image);
		track = new Track("track234", "track name", "new comments", "123", "new track url", artist);
	}
	
	@After
	public void tearDown() {
		image = null;
		artist = null;
		track = null;
		muzixRepository.deleteAll();
	}
	
	@Test
	public void testSaveTrack() {
		muzixRepository.insert(track);
		Track fetchTrack = muzixRepository.findById(track.getTrackId()).get();
		Assert.assertEquals(fetchTrack.getTrackName(),track.getTrackName());
	}
	
	@Test
	public void testUpdateComments() {
		muzixRepository.insert(track);
		Track fetchTrack = muzixRepository.findById(track.getTrackId()).get();
		fetchTrack.setComments("new comments Added");
		muzixRepository.save(fetchTrack);
		Track fetchTrackNew = muzixRepository.findById(track.getTrackId()).get();
		
		Assert.assertEquals("new comments Added",fetchTrackNew.getComments());
	}
	
	@Test
	public void testDeleteTrack() {
		muzixRepository.insert(track);
		Track fetchTrack = muzixRepository.findById(track.getTrackId()).get();
		
		muzixRepository.delete(fetchTrack);
		Assert.assertEquals(Optional.empty(),muzixRepository.findById(track.getTrackId()));
	}
	
	@Test
	public void testGetAllTracks() {
		muzixRepository.insert(track);
		
		image = new Image(2, "http:url", "large");
		artist = new Artist(1234, "Manna", "artist url", image);
		track = new Track("track1234", "my track", "additional comments", "1234", "new track url", artist);
		muzixRepository.insert(track);
		List<Track> trackList = muzixRepository.findAll();
	
		Assert.assertEquals(2,trackList.size());
		Assert.assertEquals("track1234", trackList.get(1).getTrackId());
	}
	
	
}
