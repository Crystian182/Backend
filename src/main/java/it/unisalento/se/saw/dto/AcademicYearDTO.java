package it.unisalento.se.saw.dto;

import java.util.List;

public class AcademicYearDTO {
	
	private Integer idacademicYear;
    private String years;
    private List<TermDTO> terms;
    
	public Integer getIdacademicYear() {
		return idacademicYear;
	}
	public void setIdacademicYear(Integer idacademicYear) {
		this.idacademicYear = idacademicYear;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public List<TermDTO> getTerms() {
		return terms;
	}
	public void setTerms(List<TermDTO> terms) {
		this.terms = terms;
	}
    
    

}
