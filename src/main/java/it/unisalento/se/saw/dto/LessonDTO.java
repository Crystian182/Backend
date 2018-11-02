package it.unisalento.se.saw.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Feedbacklesson;
import it.unisalento.se.saw.domain.File;
import it.unisalento.se.saw.domain.Subjectofstudy;

public class LessonDTO {

	private Integer idlesson;
    private ClassroomDTO classroom;
    private SubjectDTO subjectofstudy;
    private int day;
    private Date start;
    private Date end;
    private Set<Feedbacklesson> feedbacklessons = new HashSet<Feedbacklesson>(0);
    private Set<File> files = new HashSet<File>(0);
    
    
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
	public SubjectDTO getSubjectofstudy() {
		return subjectofstudy;
	}
	public void setSubjectofstudy(SubjectDTO subjectofstudy) {
		this.subjectofstudy = subjectofstudy;
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
	public Set<Feedbacklesson> getFeedbacklessons() {
		return feedbacklessons;
	}
	public void setFeedbacklessons(Set<Feedbacklesson> feedbacklessons) {
		this.feedbacklessons = feedbacklessons;
	}
	public Set<File> getFiles() {
		return files;
	}
	public void setFiles(Set<File> files) {
		this.files = files;
	}

    
    
}
