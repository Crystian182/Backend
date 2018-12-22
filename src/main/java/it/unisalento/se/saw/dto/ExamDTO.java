package it.unisalento.se.saw.dto;

import java.util.Date;

public class ExamDTO {

	  private Integer idexam;
	  private ClassroomDTO classroom;
	  private SubjectDTO subject;
	  private ExamTypeDTO examtype;
	  private Date date;
	  
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
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public ExamTypeDTO getExamtype() {
			return examtype;
		}
		public void setExamtype(ExamTypeDTO examtype) {
			this.examtype = examtype;
		}
	
		
}
