package it.unisalento.se.saw.dto;

import java.util.Date;

public class FileLessonDTO {

    private FileDTO file;
    private LessonDTO lesson;
    private Date date;
    
	public FileDTO getFile() {
		return file;
	}
	public void setFile(FileDTO file) {
		this.file = file;
	}
	public LessonDTO getLesson() {
		return lesson;
	}
	public void setLesson(LessonDTO lesson) {
		this.lesson = lesson;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
    
    
}
