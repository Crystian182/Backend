package it.unisalento.se.saw.dto;

import java.util.Date;
import java.util.List;

public class TypeLessonDTO {
	
	 private Integer idtypeLesson;
	 private Date start;
     private Date end;
     private DayDTO day;
     private SubjectDTO subject;
     private ClassroomDTO classroom;
     private SchedulerDTO scheduler;
     
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
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
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
     
     

}
