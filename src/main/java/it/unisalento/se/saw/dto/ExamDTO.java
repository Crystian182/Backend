package it.unisalento.se.saw.dto;

import java.util.Date;

public class ExamDTO {

	  private Integer idexam;
	  private ClassroomDTO classroom;
<<<<<<< HEAD
	  private SubjectDTO subjectofstudy;
	  private String type;
=======
	  private SubjectDTO subject;
	  private int idExamType;
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098
	  private Date date;
	  private Date start;
	  private Date end;
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
<<<<<<< HEAD
		public SubjectDTO getSubjectofstudy() {
			return subjectofstudy;
		}
		public void setSubjectofstudy(SubjectDTO subjectofstudy) {
			this.subjectofstudy = subjectofstudy;
=======
		
		public SubjectDTO getSubject() {
			return subject;
		}
		public void setSubject(SubjectDTO subject) {
			this.subject = subject;
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098
		}
		
		public int getIdExamType() {
			return idExamType;
		}
		public void setIdExamType(int idExamType) {
			this.idExamType = idExamType;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
	
		public Date getStart() {
			return start;
		}
		public void setStart(Date start) {
			this.start = start;
		}
		public Date getEnd() {
			return end;
		}
		public void setEnd(Date end) {
			this.end = end;
		}
		public boolean isSubscribed() {
			return isSubscribed;
		}
		public void setSubscribed(boolean isSubscribed) {
			this.isSubscribed = isSubscribed;
		}
		
	     
}
