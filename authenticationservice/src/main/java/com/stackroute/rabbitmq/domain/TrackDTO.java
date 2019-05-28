package com.stackroute.rabbitmq.domain;

public class TrackDTO {

	private int artistId;
	private String artistName;
	private String url;	
	private ImageDTO image;
	public TrackDTO(int artistId, String artistName, String url, ImageDTO image) {
		super();
		this.artistId = artistId;
		this.artistName = artistName;
		this.url = url;
		this.image = image;
	}
	public TrackDTO() {
		super();
	}
	public int getArtistId() {
		return artistId;
	}
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ImageDTO getImage() {
		return image;
	}
	public void setImage(ImageDTO image) {
		this.image = image;
	}
	
	
}
