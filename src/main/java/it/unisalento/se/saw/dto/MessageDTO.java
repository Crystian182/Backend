package it.unisalento.se.saw.dto;

import java.util.Date;

public class MessageDTO {
	
	private int id;
	private UserDTO sender;
	private String text;
	private Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserDTO getSender() {
		return sender;
	}
	public void setSender(UserDTO sender) {
		this.sender = sender;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	

}
