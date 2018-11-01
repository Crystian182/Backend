package it.unisalento.se.saw.dto;

import java.util.Date;

public class ExamDTO {

	  private Integer idexam;
	  private ClassroomDTO classroom;
	  private SubjectDTO subject;
	  private int idExamType;
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
		
		public SubjectDTO getSubject() {
			return subject;
		}
		public void setSubject(SubjectDTO subject) {
			this.subject = subject;
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
