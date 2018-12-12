package it.unisalento.se.saw.dto;

import java.util.List;

public class DegreeCourseDTO {


	private int idcourse;
    private int cfu;
    private TypeDegreeCourseDTO typeDegreeCourse;
    private AcademicYearDTO academicYear;
    private List<SubjectDTO> subjects;
  
   
	public Integer getIdcourse() {
		return idcourse;
	}
	public void setIdcourse(Integer idcourse) {
		this.idcourse = idcourse;
	}
	public int getCfu() {
		return cfu;
	}
	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
	public TypeDegreeCourseDTO getTypeDegreeCourse() {
		return typeDegreeCourse;
	}
	public void setTypeDegreeCourse(TypeDegreeCourseDTO typeDegreeCourse) {
		this.typeDegreeCourse = typeDegreeCourse;
	}
	public void setIdcourse(int idcourse) {
		this.idcourse = idcourse;
	}
	public AcademicYearDTO getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(AcademicYearDTO academicYear) {
		this.academicYear = academicYear;
	}
	public List<SubjectDTO> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<SubjectDTO> subjects) {
		this.subjects = subjects;
	}
	

}
