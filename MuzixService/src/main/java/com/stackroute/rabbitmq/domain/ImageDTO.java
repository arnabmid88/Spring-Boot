package com.stackroute.rabbitmq.domain;


public class ImageDTO {

	private int imageId;
	private String imageUrl;
	private String imageSpec;
	public ImageDTO(int imageId, String imageUrl, String imageSpec) {
		super();
		this.imageId = imageId;
		this.imageUrl = imageUrl;
		this.imageSpec = imageSpec;
	}
	public ImageDTO() {
		super();
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageSpec() {
		return imageSpec;
	}
	public void setImageSpec(String imageSpec) {
		this.imageSpec = imageSpec;
	}
	
	
}
