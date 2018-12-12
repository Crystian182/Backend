package it.unisalento.se.saw.dto;

import java.util.List;
import it.unisalento.se.saw.domain.TypeLesson;

public class SchedulerDTO {
	
	private int idScheduler;
    private DegreeCourseDTO degreeCourse;
    private TermDTO term;
    private String name;
    private List<TypeLessonDTO> typeLessons;
    
	public int getIdScheduler() {
		return idScheduler;
	}
	public void setIdScheduler(int idScheduler) {
		this.idScheduler = idScheduler;
	}
	public DegreeCourseDTO getDegreeCourse() {
		return degreeCourse;
	}
	public void setDegreeCourse(DegreeCourseDTO degreeCourse) {
		this.degreeCourse = degreeCourse;
	}
	public TermDTO getTerm() {
		return term;
	}
	public void setTerm(TermDTO term) {
		this.term = term;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TypeLessonDTO> getTypeLessons() {
		return typeLessons;
	}
	public void setTypeLessons(List<TypeLessonDTO> typeLessons) {
		this.typeLessons = typeLessons;
	}
    
    

}
