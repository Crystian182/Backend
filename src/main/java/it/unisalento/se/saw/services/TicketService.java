package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ITicketService;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Employee;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.TicketMessage;
import it.unisalento.se.saw.domain.TicketStatus;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.EmployeeDTO;
import it.unisalento.se.saw.dto.TicketStatusDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TicketDTO;
import it.unisalento.se.saw.dto.TicketMessageDTO;
import it.unisalento.se.saw.exceptions.TicketNotFoundException;
import it.unisalento.se.saw.repositories.TicketRepository;


@Service
public class TicketService implements ITicketService {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Transactional(readOnly = true)
	public List<TicketDTO> getAll() {
		List<Ticket> tickets = ticketRepository.findAll();
		List<TicketDTO> ticketDTOs = new ArrayList<TicketDTO>();
		for(int i=0; i<tickets.size(); i++) {
			
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(tickets.get(i).getTeacher().getIduser());
			teacherDTO.setName(tickets.get(i).getTeacher().getUser().getName());
			teacherDTO.setSurname(tickets.get(i).getTeacher().getUser().getSurname());
			EmployeeDTO employeeDTO = new EmployeeDTO();
			try {
			employeeDTO.setIdemployee(tickets.get(i).getEmployee().getIduser());
			employeeDTO.setName(tickets.get(i).getEmployee().getUser().getName());
			employeeDTO.setSurname(tickets.get(i).getEmployee().getUser().getSurname());
			} catch (Exception e) {
				
			}
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(tickets.get(i).getClassroom().getBuilding().getIdbuilding());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(tickets.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(tickets.get(i).getClassroom().getName());
			classroomDTO.setBuilding(buildingDTO);
			
			TicketStatusDTO ticketStatusDTO = new TicketStatusDTO();
			ticketStatusDTO.setIdstatus(tickets.get(i).getTicketStatus().getIdticketStatus());
			ticketStatusDTO.setDescription(tickets.get(i).getTicketStatus().getDescription());
			
			TicketDTO ticketDTO = new TicketDTO();
			ticketDTO.setId(tickets.get(i).getIdticket());
			ticketDTO.setTitle(tickets.get(i).getTitle());
			ticketDTO.setTicketStatus(ticketStatusDTO);
			try {
			ticketDTO.setEmployee(employeeDTO);
			} catch (Exception e) {
				
			}
			ticketDTO.setTeacher(teacherDTO);
			ticketDTO.setClassroom(classroomDTO);
			ticketDTO.setDate(tickets.get(i).getDate());
			ticketDTOs.add(ticketDTO);
		}
		return ticketDTOs;
		
	}
	
	@Transactional(rollbackFor=TicketNotFoundException.class)
	public TicketDTO getById(int id) throws TicketNotFoundException{
		try {
			Ticket ticket = ticketRepository.findById(id).get();
			List<TicketMessage> ticketMessages = ticketRepository.getMessages(ticket.getIdticket());
			
			List<TicketMessageDTO> ticketMessageDTOs = new ArrayList<TicketMessageDTO>();
			for(int i=0; i<ticketMessages.size(); i++) {
				UserDTO userDTO = new UserDTO();
				userDTO.setIduser(ticketMessages.get(i).getUser().getIduser());
				userDTO.setSurname(ticketMessages.get(i).getUser().getSurname());
				userDTO.setName(ticketMessages.get(i).getUser().getName());	
				TicketMessageDTO ticketMessageDTO = new TicketMessageDTO();
				ticketMessageDTO.setIdticketmessage(ticketMessages.get(i).getIdticketMessage());
				ticketMessageDTO.setIdticket(ticketMessages.get(i).getTicket().getIdticket());
				ticketMessageDTO.setDate(ticketMessages.get(i).getDate());
				ticketMessageDTO.setText(ticketMessages.get(i).getText());
				ticketMessageDTO.setUser(userDTO);
				ticketMessageDTOs.add(ticketMessageDTO);
			}
			
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(ticket.getTeacher().getIduser());
			teacherDTO.setName(ticket.getTeacher().getUser().getName());
			teacherDTO.setSurname(ticket.getTeacher().getUser().getSurname());
			
			EmployeeDTO employeeDTO = new EmployeeDTO();
			try {
			employeeDTO.setIdemployee(ticket.getEmployee().getIduser());
			employeeDTO.setName(ticket.getEmployee().getUser().getName());
			employeeDTO.setSurname(ticket.getEmployee().getUser().getSurname());
			} catch (Exception e) {
			}
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(ticket.getClassroom().getBuilding().getIdbuilding());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(ticket.getClassroom().getIdclassroom());
			classroomDTO.setName(ticket.getClassroom().getName());
			classroomDTO.setBuilding(buildingDTO);
			
			TicketStatusDTO ticketStatusDTO = new TicketStatusDTO();
			ticketStatusDTO.setIdstatus(ticket.getTicketStatus().getIdticketStatus());
			ticketStatusDTO.setDescription(ticket.getTicketStatus().getDescription());
			
			TicketDTO ticketDTO = new TicketDTO();
			ticketDTO.setId(ticket.getIdticket());
			ticketDTO.setTitle(ticket.getTitle());
			ticketDTO.setTicketStatus(ticketStatusDTO);
			try {
			ticketDTO.setEmployee(employeeDTO);
			} catch (Exception e) {
			}
			ticketDTO.setTeacher(teacherDTO);
			ticketDTO.setClassroom(classroomDTO);
			ticketDTO.setDate(ticket.getDate());
			ticketDTO.setTicketmessages(ticketMessageDTOs);
			return ticketDTO;
			
		} catch (Exception e) {
			throw new TicketNotFoundException();
		}
		
	}
	
	@Transactional
	public List<TicketMessageDTO> getMessages(int idticket) {
		List<TicketMessage> ticketMessages = ticketRepository.getMessages(idticket);
		List<TicketMessageDTO> ticketMessageDTOs = new ArrayList<TicketMessageDTO>();
		for(int i=0; i<ticketMessages.size(); i++) {
			UserDTO userDTO = new UserDTO();
			userDTO.setIduser(ticketMessages.get(i).getUser().getIduser());
			userDTO.setSurname(ticketMessages.get(i).getUser().getSurname());
			userDTO.setName(ticketMessages.get(i).getUser().getName());	
			TicketMessageDTO ticketMessageDTO = new TicketMessageDTO();
			ticketMessageDTO.setIdticketmessage(ticketMessages.get(i).getIdticketMessage());
			ticketMessageDTO.setIdticket(ticketMessages.get(i).getTicket().getIdticket());
			ticketMessageDTO.setDate(ticketMessages.get(i).getDate());
			ticketMessageDTO.setText(ticketMessages.get(i).getText());
			ticketMessageDTO.setUser(userDTO);
			ticketMessageDTOs.add(ticketMessageDTO);
		}
		return ticketMessageDTOs;
	}
	
	@Transactional
	public TicketDTO save(TicketDTO ticketDTO) {
		Teacher teacher = new Teacher();
		teacher.setIduser(ticketDTO.getTeacher().getIdteacher());
		Employee employee = new Employee();
		employee.setIduser(ticketDTO.getEmployee().getIdemployee());
		
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(ticketDTO.getClassroom().getId());
		
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(ticketDTO.getTicketStatus().getIdstatus());
		ticketStatus.setDescription(ticketDTO.getTicketStatus().getDescription());
		
		Ticket ticket = new Ticket();
		try {
			ticket.setIdticket(ticketDTO.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		ticket.setTitle(ticketDTO.getTitle());
		ticket.setDate(ticketDTO.getDate());
		ticket.setTeacher(teacher);
		ticket.setClassroom(classroom);
		ticket.setTicketStatus(ticketStatus);
		ticket.setEmployee(employee);
		
		Ticket newTicket = ticketRepository.save(ticket);
		 
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setIdteacher(newTicket.getTeacher().getIduser());
		teacherDTO.setName(newTicket.getTeacher().getUser().getName());
		teacherDTO.setSurname(newTicket.getTeacher().getUser().getSurname());
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setIdemployee(newTicket.getEmployee().getIduser());
		employeeDTO.setName(newTicket.getEmployee().getUser().getName());
		employeeDTO.setSurname(newTicket.getEmployee().getUser().getSurname());
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(newTicket.getClassroom().getBuilding().getIdbuilding());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(newTicket.getClassroom().getIdclassroom());
		classroomDTO.setName(newTicket.getClassroom().getName());
		classroomDTO.setBuilding(buildingDTO);
		
		TicketStatusDTO ticketStatusDTO = new TicketStatusDTO();
		ticketStatusDTO.setIdstatus(newTicket.getTicketStatus().getIdticketStatus());
		ticketStatusDTO.setDescription(newTicket.getTicketStatus().getDescription());
		
		TicketDTO newTicketDTO = new TicketDTO();
		newTicketDTO.setId(newTicket.getIdticket());
		newTicketDTO.setTitle(newTicket.getTitle());
		newTicketDTO.setTicketStatus(ticketStatusDTO);
		newTicketDTO.setEmployee(employeeDTO);
		newTicketDTO.setTeacher(teacherDTO);
		newTicketDTO.setClassroom(classroomDTO);
		newTicketDTO.setDate(newTicket.getDate());
		return newTicketDTO;
		
	}
	
	

	@Transactional
	public void delete(int id) throws TicketNotFoundException{
		try {
			ticketRepository.deleteById(id);
		} catch (Exception e) {
			throw new TicketNotFoundException();
		}
		
	}
	
	@Transactional
	public TicketDTO saveMessages(TicketDTO ticketDTO, TicketMessageDTO ticketMessageDTO) {
		List<TicketMessageDTO> ticketMessageDTOs = ticketDTO.getTicketmessages();
		ticketMessageDTOs.add(ticketMessageDTO);
		ticketDTO.setTicketmessages(ticketMessageDTOs);
		return ticketDTO;
	}
	
}
