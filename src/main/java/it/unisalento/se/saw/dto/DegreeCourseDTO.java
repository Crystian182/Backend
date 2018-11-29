package it.unisalento.se.saw.dto;

public class DegreeCourseDTO {


	private int idcourse;
    private int cfu;
    private TypeDegreeCourseDTO typeDegreeCourse;
    private String academicYear;
  
   
    
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
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	

}
