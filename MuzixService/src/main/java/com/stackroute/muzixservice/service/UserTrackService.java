package com.stackroute.muzixservice.service;

import java.util.List;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.domain.User;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;
import com.stackroute.muzixservice.exception.UserAlreadyExistsException;

public interface UserTrackService {

	User saveUserTrackToWishlist(Track track, String username) throws TrackAlreadyExistsException;
	User deleteUserTrackFromWishList(String username, String trackId) throws TrackNotFoundException;
	User updateCommentForTrack(String comment, String trackId, String username) throws TrackNotFoundException;
	List<Track> getAllUserTrackFromWishlist(String username) throws Exception;
	User registerUser(User user) throws UserAlreadyExistsException;
}
