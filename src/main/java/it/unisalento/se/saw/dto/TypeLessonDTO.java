package it.unisalento.se.saw.dto;

import java.util.Date;
import java.util.List;

public class TypeLessonDTO {
	
	 private Integer idtypeLesson;
     private ClassroomDTO classroom;
     private DayDTO day;
     private SchedulerDTO scheduler;
     private TypeSubjectDTO typeSubject;
     private Date start;
     private Date end;
     private List<LessonDTO> lessons;
     
	public Integer getIdtypeLesson() {
		return idtypeLesson;
	}
	public void setIdtypeLesson(Integer idtypeLesson) {
		this.idtypeLesson = idtypeLesson;
	}
	public ClassroomDTO getClassroom() {
		return classroom;
	}
	public void setClassroom(ClassroomDTO classroom) {
		this.classroom = classroom;
	}
	public DayDTO getDay() {
		return day;
	}
	public void setDay(DayDTO day) {
		this.day = day;
	}
	public SchedulerDTO getScheduler() {
		return scheduler;
	}
	public void setScheduler(SchedulerDTO scheduler) {
		this.scheduler = scheduler;
	}
	public TypeSubjectDTO getTypeSubject() {
		return typeSubject;
	}
	public void setTypeSubject(TypeSubjectDTO typeSubject) {
		this.typeSubject = typeSubject;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public List<LessonDTO> getLessons() {
		return lessons;
	}
	public void setLessons(List<LessonDTO> lessons) {
		this.lessons = lessons;
	}
     
     

}
