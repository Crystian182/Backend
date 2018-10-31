package it.unisalento.se.saw.dto;

import java.util.Date;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Subjectofstudy;

public class ConfirmationDTO {
	
	private Integer idexam;
	private Integer iduser; 
	private String nameuser;
	private String surnameuser;
	private Date date;
	private Subjectofstudy subjectofstudy;
	private String type;
	private Classroom classroom;
	
	public Integer getIdexam() {
		return idexam;
	}
	public void setIdexam(Integer idexam) {
		this.idexam = idexam;
	}
	public Integer getIduser() {
		return iduser;
	}
	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}
	public String getNameuser() {
		return nameuser;
	}
	public void setNameuser(String nameuser) {
		this.nameuser = nameuser;
	}
	public String getSurnameuser() {
		return surnameuser;
	}
	public void setSurnameuser(String surnameuser) {
		this.surnameuser = surnameuser;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Subjectofstudy getSubjectofstudy() {
		return subjectofstudy;
	}
	public void setSubjectofstudy(Subjectofstudy subjectofstudy) {
		this.subjectofstudy = subjectofstudy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Classroom getClassroom() {
		return classroom;
	}
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	

}
