package it.unisalento.se.saw.dto;

import java.util.Date;


public class TicketMessageDTO {

	private Integer idticketmessage;
	 private Integer idticket;
     private UserDTO user;
     private String text;
     private Date date;
     
     
	public Integer getIdticketmessage() {
		return idticketmessage;
	}
	public void setIdticketmessage(Integer idticketmessage) {
		this.idticketmessage = idticketmessage;
	}
	
	public Integer getIdticket() {
		return idticket;
	}
	public void setIdticket(Integer idticket) {
		this.idticket = idticket;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
     
     
     
     
}
