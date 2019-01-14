package it.unisalento.se.saw.dto;

import java.util.Date;

public class StudentHasDegreeCourseDTO {
	 
	  private DegreeCourseDTO degreeCourse;
	  private EnrollmentStatusDTO enrollmentStatus;
	  private StudentDTO student;
	  private Date date;

	  
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public DegreeCourseDTO getDegreeCourse() {
		return degreeCourse;
	}
	public void setDegreeCourse(DegreeCourseDTO degreeCourse) {
		this.degreeCourse = degreeCourse;
	}
	public EnrollmentStatusDTO getEnrollmentStatus() {
		return enrollmentStatus;
	}
	public void setEnrollmentStatus(EnrollmentStatusDTO enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	  
}
