package it.unisalento.se.saw.dto;

import java.util.Date;

import it.unisalento.se.saw.domain.AcademicYear;

public class TermDTO {
	
	 private Integer idterm;
     private AcademicYear academicYear;
     private int number;
     private Date start;
     private Date end;
     
     
	public Integer getIdterm() {
		return idterm;
	}
	public void setIdterm(Integer idterm) {
		this.idterm = idterm;
	}
	public AcademicYear getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
     
     

}