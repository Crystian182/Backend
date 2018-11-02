package it.unisalento.se.saw.dto;

import java.util.Date;
import java.util.List;


public class TicketDTO {
	
	private int id;
	private String title;
	private TeacherDTO teacher;
	private EmployeeDTO employee;
	private TicketStatusDTO ticketStatus;
	private ClassroomDTO classroom;
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
	public EmployeeDTO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}
	public TicketStatusDTO getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(TicketStatusDTO ticketStatus) {
		this.ticketStatus = ticketStatus;
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
