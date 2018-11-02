package it.unisalento.se.saw.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class LessonDTO {

	private Integer idlesson;
    private ClassroomDTO classroom;
<<<<<<< HEAD
    private SubjectDTO subjectofstudy;
=======
    private SubjectDTO subject;
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098
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
<<<<<<< HEAD
	public SubjectDTO getSubjectofstudy() {
		return subjectofstudy;
	}
	public void setSubjectofstudy(SubjectDTO subjectofstudy) {
		this.subjectofstudy = subjectofstudy;
=======
	
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098
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
