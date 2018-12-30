package it.unisalento.se.saw.dto;

import java.util.Date;

public class ExamEnrollmentDTO {
	
	private StudentDTO student;
	private Date date;
	private int grade;
	private ResultDTO result;
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public ResultDTO getResult() {
		return result;
	}
	public void setResult(ResultDTO result) {
		this.result = result;
	}
	

}
