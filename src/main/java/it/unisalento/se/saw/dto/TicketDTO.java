package it.unisalento.se.saw.dto;

import java.util.Date;
import java.util.List;


public class TicketDTO {
	
	private int id;
	private String title;
	private StatusDTO status;
	private TeacherDTO teacher;
	private ClassroomDTO classroom;
	private String text;
	private Date date;
    private List<TicketMessageDTO> ticketmessages;
    
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	public TeacherDTO getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}
	public ClassroomDTO getClassroom() {
		return classroom;
	}
	public void setClassroom(ClassroomDTO classroom) {
		this.classroom = classroom;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<TicketMessageDTO> getTicketmessages() {
		return ticketmessages;
	}
	public void setTicketmessages(List<TicketMessageDTO> ticketmessages) {
		this.ticketmessages = ticketmessages;
	}
	

}
