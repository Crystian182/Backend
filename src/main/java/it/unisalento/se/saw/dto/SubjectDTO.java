package it.unisalento.se.saw.dto;

public class SubjectDTO {
<<<<<<< HEAD:src/main/java/it/unisalento/se/saw/dto/SubjectDTO.java
=======

>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098:src/main/java/it/unisalento/se/saw/dto/SubjectDTO.java
	
	private int id;
	private String name;
	private String description;
	private DegreeCourseDTO degreecourseDTO;
	private TeacherDTO teacherDTO;
	private int cfu;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public TeacherDTO getTeacherDTO() {
		return teacherDTO;
	}
	public void setTeacherDTO(TeacherDTO teacherDTO) {
		this.teacherDTO = teacherDTO;
	}
	public DegreeCourseDTO getDegreecourseDTO() {
		return degreecourseDTO;
	}
	public void setDegreecourseDTO(DegreeCourseDTO degreecourseDTO) {
		this.degreecourseDTO = degreecourseDTO;
	}
	public int getCfu() {
		return cfu;
	}
	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
	
	
}
