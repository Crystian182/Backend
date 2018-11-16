package it.unisalento.se.saw.dto;

import java.util.Date;

public class TeacherDTO {
	
	private Integer idteacher;
	private String name;
	private String surname;
	private Date dateBirth;
	private String placeBirth;
	private String domicile;
	private String residence;
	
	

	public Integer getIdteacher() {
		return idteacher;
	}
	public void setIdteacher(Integer idteacher) {
		this.idteacher = idteacher;
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
	public String getPlaceBirth() {
		return placeBirth;
	}
	public void setPlaceBirth(String placeBirth) {
		this.placeBirth = placeBirth;
	}

	public String getDomicile() {
		return domicile;
	}
	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	
	
	

}
