package com.stackroute.muzixservice.service;

import java.util.List;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;

public interface MuzixServices {
	
	Track saveTrackToWishList(Track track) throws TrackAlreadyExistsException;
	
	boolean deleteTrackFromWishList(String id) throws TrackNotFoundException;
	
	Track updateCommentForTrack(String comments, String id) throws TrackNotFoundException;
	
	List<Track> getAllTracksFromWishlist() throws Exception;

}
