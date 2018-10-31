package it.unisalento.se.saw.models;

import it.unisalento.se.saw.domain.Type;

public class Login {
	
	String email;
	String password;
	Type type;

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
