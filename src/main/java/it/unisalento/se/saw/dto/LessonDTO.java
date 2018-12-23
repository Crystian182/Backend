package it.unisalento.se.saw.dto;

import java.util.Date;


public class LessonDTO {

	private Integer idlesson;
    private ClassroomDTO classroom;
    private Date start;
    private Date end;
    private TypeLessonDTO typeLesson;
    
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
	public TypeLessonDTO getTypeLesson() {
		return typeLesson;
	}
	public void setTypeLesson(TypeLessonDTO typeLesson) {
		this.typeLesson = typeLesson;
	}
	
	

    
    
}
