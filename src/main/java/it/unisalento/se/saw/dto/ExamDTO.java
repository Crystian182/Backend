package it.unisalento.se.saw.dto;

import java.util.Date;
import java.util.List;

public class ExamDTO {

	  private Integer idexam;
	  private ClassroomDTO classroom;
	  private SubjectDTO subject;
	  private ExamTypeDTO examtype;
	  private Date date;
	  private ExamStatusDTO status;
	  private List<ExamEnrollmentDTO> enrollments;
	  
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
		public ExamStatusDTO getStatus() {
			return status;
		}
		public void setStatus(ExamStatusDTO status) {
			this.status = status;
		}
		public List<ExamEnrollmentDTO> getEnrollments() {
			return enrollments;
		}
		public void setEnrollments(List<ExamEnrollmentDTO> enrollments) {
			this.enrollments = enrollments;
		}
		
	
		
}
