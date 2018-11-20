package it.unisalento.se.saw.dto;

public class DegreeCourseDTO {


	private int idcourse;
    private String name;
    private int cfu;
    private int idTypeDegreeCourse;
    private String academicYear;
  
   
    
	public Integer getIdcourse() {
		return idcourse;
	}
	public void setIdcourse(Integer idcourse) {
		this.idcourse = idcourse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCfu() {
		return cfu;
	}
	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
	public int getIdTypeDegreeCourse() {
		return idTypeDegreeCourse;
	}
	public void setIdTypeDegreeCourse(int idTypeDegreeCourse) {
		this.idTypeDegreeCourse = idTypeDegreeCourse;
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
