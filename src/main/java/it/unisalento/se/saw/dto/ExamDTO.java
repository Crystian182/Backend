package it.unisalento.se.saw.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ExamHasUser;
import it.unisalento.se.saw.domain.Subjectofstudy;

public class ExamDTO {

	  private Integer idexam;
	  private ClassroomDTO classroom;
	  private SubjectDTO subjectofstudy;
	  private String type;
	  private Date date;
	  private Date startregistration;
	  private Date endregistration;
	  private boolean isSubscribed;
	 
	  
	  
		public Integer getIdexam() {
			return idexam;
		}
		public void setIdexam(Integer idexam) {
			this.idexam = idexam;
		}
		public ClassroomDTO getClassroom() {
			return classroom;
		}
		public void setClassroom(ClassroomDTO classroom) {
			this.classroom = classroom;
		}
		public SubjectDTO getSubjectofstudy() {
			return subjectofstudy;
		}
		public void setSubjectofstudy(SubjectDTO subjectofstudy) {
			this.subjectofstudy = subjectofstudy;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public Date getStartregistration() {
			return startregistration;
		}
		public void setStartregistration(Date startregistration) {
			this.startregistration = startregistration;
		}
		public Date getEndregistration() {
			return endregistration;
		}
		public void setEndregistration(Date endregistration) {
			this.endregistration = endregistration;
		}
		public boolean isSubscribed() {
			return isSubscribed;
		}
		public void setSubscribed(boolean isSubscribed) {
			this.isSubscribed = isSubscribed;
		}
		
	     
}
