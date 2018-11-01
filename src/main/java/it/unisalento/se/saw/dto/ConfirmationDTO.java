package it.unisalento.se.saw.dto;

import java.util.Date;


public class ConfirmationDTO {
	
	private Integer idexam;
	private Integer iduser; 
	private String nameuser;
	private String surnameuser;
	private Date date;
	private SubjectDTO subject;
	private String type;
	private ClassroomDTO classroom;
	
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

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
	public ClassroomDTO getClassroom() {
		return classroom;
	}
	public void setClassroom(ClassroomDTO classroom) {
		this.classroom = classroom;
	}
	
	

}
