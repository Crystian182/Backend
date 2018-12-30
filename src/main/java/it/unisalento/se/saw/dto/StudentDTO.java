package it.unisalento.se.saw.dto;

import java.util.Date;

public class StudentDTO {

	private Integer idstudent;
	private String name;
	private String surname;
	private Date dateBirth;
	private String email;

	public Integer getIdstudent() {
		return idstudent;
	}
	public void setIdstudent(Integer idstudent) {
		this.idstudent = idstudent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getDateBirth() {
		return dateBirth;
	}
	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
