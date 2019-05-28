package com.stackroute.muzixservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.muzixservice.config.Producer;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.domain.User;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;
import com.stackroute.muzixservice.exception.UserAlreadyExistsException;
import com.stackroute.muzixservice.repository.UserTrackRepository;
import com.stackroute.rabbitmq.domain.UserDTO;

@Service
public class UserTrackServiceImpl implements UserTrackService{
	
	private UserTrackRepository userTrackRepository;
	private Producer producer;

	@Autowired	
	public UserTrackServiceImpl(UserTrackRepository userTrackRepository, Producer producer) {
		super();
		this.userTrackRepository = userTrackRepository;
		this.producer = producer;
	}

	@Override
	public User saveUserTrackToWishlist(Track track, String username) throws TrackAlreadyExistsException {
		User fetchUser = userTrackRepository.findByUsername(username);
		List<Track> trackList = fetchUser.getTrackList();
		if(trackList != null) {
			
			for(Track trackObj: trackList) {
				if(trackObj.getTrackId().equals(track.getTrackId())) {
					throw new TrackAlreadyExistsException();
				}
			}
			trackList.add(track);
			fetchUser.setTrackList(trackList);
			
			UserDTO userDto = new UserDTO();
			userDto.setUsername(username);
			userDto.setEmail(fetchUser.getEmail());
			List list = new ArrayList();
			list.add(trackList);
			userDto.setTrackList(list);
			userTrackRepository.save(fetchUser);
			producer.sendTrackToRabbitMQ(userDto);
			
		}else {
			trackList = new ArrayList<Track>();
			trackList.add(track);
			fetchUser.setTrackList(trackList);
			
			UserDTO userDto = new UserDTO();
			userDto.setUsername(username);
			userDto.setEmail(fetchUser.getEmail());
			List list = new ArrayList();
			list.add(trackList);
			userDto.setTrackList(list);
			userTrackRepository.save(fetchUser);
			producer.sendTrackToRabbitMQ(userDto);
		}
//		userTrackRepository.save(fetchUser);
		return fetchUser;
	}

	@Override
	public User deleteUserTrackFromWishList(String username, String trackId) throws TrackNotFoundException {
		User fetchUser = userTrackRepository.findByUsername(username);
		List<Track> fetchTracks = fetchUser.getTrackList();
		
		if(fetchTracks.size()>0) {
			for(int i=0;i<fetchTracks.size();i++) {
				if(trackId.equals(fetchTracks.get(i).getTrackId())) {
					fetchTracks.remove(i);
					fetchUser.setTrackList(fetchTracks);
					userTrackRepository.save(fetchUser);
					break;
				}
			}
		}else {
			throw new TrackNotFoundException();
		}
		
		return fetchUser;
	}

	@Override
	public User updateCommentForTrack(String comment, String trackId, String username) throws TrackNotFoundException {
		User fetchUser = userTrackRepository.findByUsername(username);
		List<Track> fetchTracks = fetchUser.getTrackList();
		
		if(fetchTracks.size()>0) {
			for(int i=0;i<fetchTracks.size();i++) {
				if(trackId.equals(fetchTracks.get(i).getTrackId())) {
					fetchTracks.get(i).setComments(comment);
					fetchUser.setTrackList(fetchTracks);
					userTrackRepository.save(fetchUser);
					break;
				}
			}
		}else {
			throw new TrackNotFoundException();
		}
		
		return fetchUser;
	}

	@Override
	public List<Track> getAllUserTrackFromWishlist(String username) throws Exception {
		User fetchUser = userTrackRepository.findByUsername(username);
		
		return fetchUser.getTrackList();
	}

	@Override
	public User registerUser(User user) throws UserAlreadyExistsException {
		UserDTO userDto = new UserDTO();
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		User fetchUser = userTrackRepository.findByUsername(user.getUsername());
		
		if(fetchUser != null) {
			throw new UserAlreadyExistsException();
		}else {
			userTrackRepository.save(user);
			producer.sendMessageToRabbitMQ(userDto);
			return user;
		}
	}

}
