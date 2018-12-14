package it.unisalento.se.saw.dto;

import java.util.List;

public class AcademicYearDTO {
	
	private Integer idacademicYear;
    private Integer year;
    private List<TermDTO> terms;
    
	public Integer getIdacademicYear() {
		return idacademicYear;
	}
	public void setIdacademicYear(Integer idacademicYear) {
		this.idacademicYear = idacademicYear;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<TermDTO> getTerms() {
		return terms;
	}
	public void setTerms(List<TermDTO> terms) {
		this.terms = terms;
	}
    
    

}
