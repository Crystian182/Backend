package it.unisalento.se.saw.dto;

public class DegreeCourseDTO {


	private Integer idcourse;
    private String name;
    private String description;
    private int idCourseType;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIdCourseType() {
		return idCourseType;
	}
	public void setIdCourseType(int idCourseType) {
		this.idCourseType = idCourseType;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	

}
