package it.unisalento.se.saw.dto;

import java.util.List;
import it.unisalento.se.saw.domain.TypeLesson;

public class SchedulerDTO {
	
	private int idScheduler;
    private DegreeCourseDTO degreeCourse;
    private TermDTO term;
    private List<TypeLessonDTO> typeLessons;
    private List<LessonDTO> lessons;
    
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
	public List<TypeLessonDTO> getTypeLessons() {
		return typeLessons;
	}
	public void setTypeLessons(List<TypeLessonDTO> typeLessons) {
		this.typeLessons = typeLessons;
	}
	public List<LessonDTO> getLessons() {
		return lessons;
	}
	public void setLessons(List<LessonDTO> lessons) {
		this.lessons = lessons;
	}
    
    

}
