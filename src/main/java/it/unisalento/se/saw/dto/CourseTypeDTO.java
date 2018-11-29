package it.unisalento.se.saw.dto;

public class CourseTypeDTO {

	private Integer idcourseType;
    private String description;
    private int duration;
    private int cfu;
	
    
    public Integer getIdcourseType() {
		return idcourseType;
	}
	public void setIdcourseType(Integer idcourseType) {
		this.idcourseType = idcourseType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getCfu() {
		return cfu;
	}
	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
    
    
}
