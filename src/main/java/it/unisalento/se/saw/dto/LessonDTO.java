package it.unisalento.se.saw.dto;

import java.util.Date;


public class LessonDTO {

	private Integer idlesson;
    private ClassroomDTO classroom;
    private SubjectDTO subject;
    private int day;
    private Date start;
    private Date end;
    
    
	public Integer getIdlesson() {
		return idlesson;
	}
	public void setIdlesson(Integer idlesson) {
		this.idlesson = idlesson;
	}
	public ClassroomDTO getClassroom() {
		return classroom;
	}
	public void setClassroom(ClassroomDTO classroom) {
		this.classroom = classroom;
	}
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
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
