package it.unisalento.se.saw.dto;

import java.util.Date;

import it.unisalento.se.saw.domain.User;

public class FeedbackDTO {
	
	private int id;
	private Date date;
	private String description;
	private int stars;
	private UserDTO user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	

}
