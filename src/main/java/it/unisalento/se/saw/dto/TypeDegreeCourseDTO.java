package it.unisalento.se.saw.dto;

public class TypeDegreeCourseDTO {

	private int idtypeDegreeCourse;
    private CourseTypeDTO courseType;
    private String name;
    
    
	public int getIdtypeDegreeCourse() {
		return idtypeDegreeCourse;
	}
	public void setIdtypeDegreeCourse(int idtypeDegreeCourse) {
		this.idtypeDegreeCourse = idtypeDegreeCourse;
	}
	public CourseTypeDTO getCourseType() {
		return courseType;
	}
	public void setCourseType(CourseTypeDTO courseType) {
		this.courseType = courseType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
  
    
    
    
    
}
