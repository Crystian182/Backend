package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IFCMService;
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
import it.unisalento.se.saw.repositories.TicketMessageRepository;
import it.unisalento.se.saw.repositories.TicketRepository;
import it.unisalento.se.saw.repositories.UserRepository;


@Service
public class TicketService implements ITicketService {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	TicketMessageRepository ticketMessageRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IFCMService fcmService;
	
	@Transactional(readOnly = true)
	public List<TicketDTO> getAll() {
		List<Ticket> tickets = ticketRepository.findAll();
		List<TicketDTO> ticketDTOs = new ArrayList<TicketDTO>();
		
		for(int i=0; i<tickets.size(); i++) {
			
			List<TicketMessage> ticketMessages = ticketRepository.getMessages(tickets.get(i).getIdticket());
			
			List<TicketMessageDTO> ticketMessageDTOs = new ArrayList<TicketMessageDTO>();
			for(int j=0; j<ticketMessages.size(); j++) {
				UserDTO userDTO = new UserDTO();
				userDTO.setIduser(ticketMessages.get(j).getUser().getIduser());
				userDTO.setSurname(ticketMessages.get(j).getUser().getSurname());
				userDTO.setName(ticketMessages.get(j).getUser().getName());	
				TicketMessageDTO ticketMessageDTO = new TicketMessageDTO();
				ticketMessageDTO.setIdticketmessage(ticketMessages.get(j).getIdticketMessage());
				ticketMessageDTO.setIdticket(ticketMessages.get(j).getTicket().getIdticket());
				ticketMessageDTO.setDate(ticketMessages.get(j).getDate());
				ticketMessageDTO.setText(ticketMessages.get(j).getText());
				ticketMessageDTO.setUser(userDTO);
				ticketMessageDTOs.add(ticketMessageDTO);
			}
			
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
			buildingDTO.setName(tickets.get(i).getClassroom().getBuilding().getName());
			
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
			ticketDTO.setTicketmessages(ticketMessageDTOs);
			ticketDTOs.add(ticketDTO);
		}
		return ticketDTOs;
		
	}
	
	@Transactional(readOnly = true)
	public List<TicketDTO> getAllTeacherTickets(int idteacher) {
		List<Ticket> tickets = ticketRepository.getAllTeacherTickets(idteacher);
		List<TicketDTO> ticketDTOs = new ArrayList<TicketDTO>();
		
		for(int i=0; i<tickets.size(); i++) {
			
			List<TicketMessage> ticketMessages = ticketRepository.getMessagesASC(tickets.get(i).getIdticket());
			
			List<TicketMessageDTO> ticketMessageDTOs = new ArrayList<TicketMessageDTO>();
			for(int j=0; j<ticketMessages.size(); j++) {
				UserDTO userDTO = new UserDTO();
				userDTO.setIduser(ticketMessages.get(j).getUser().getIduser());
				userDTO.setSurname(ticketMessages.get(j).getUser().getSurname());
				userDTO.setName(ticketMessages.get(j).getUser().getName());	
				TicketMessageDTO ticketMessageDTO = new TicketMessageDTO();
				ticketMessageDTO.setIdticketmessage(ticketMessages.get(j).getIdticketMessage());
				ticketMessageDTO.setIdticket(ticketMessages.get(j).getTicket().getIdticket());
				ticketMessageDTO.setDate(ticketMessages.get(j).getDate());
				ticketMessageDTO.setText(ticketMessages.get(j).getText());
				ticketMessageDTO.setUser(userDTO);
				ticketMessageDTOs.add(ticketMessageDTO);
			}
			
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
			buildingDTO.setName(tickets.get(i).getClassroom().getBuilding().getName());
			
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
			ticketDTO.setTicketmessages(ticketMessageDTOs);
			ticketDTOs.add(ticketDTO);
		}
		return ticketDTOs;
		
	}
	
	@Transactional(readOnly = true)
	public List<TicketDTO> getAllClassroomTickets(int idclassroom) {
		List<Ticket> tickets = ticketRepository.getAllClassroomTickets(idclassroom);
		List<TicketDTO> ticketDTOs = new ArrayList<TicketDTO>();
		
		for(int i=0; i<tickets.size(); i++) {
			
			List<TicketMessage> ticketMessages = ticketRepository.getMessagesASC(tickets.get(i).getIdticket());
			
			List<TicketMessageDTO> ticketMessageDTOs = new ArrayList<TicketMessageDTO>();
			for(int j=0; j<ticketMessages.size(); j++) {
				UserDTO userDTO = new UserDTO();
				userDTO.setIduser(ticketMessages.get(j).getUser().getIduser());
				userDTO.setSurname(ticketMessages.get(j).getUser().getSurname());
				userDTO.setName(ticketMessages.get(j).getUser().getName());	
				TicketMessageDTO ticketMessageDTO = new TicketMessageDTO();
				ticketMessageDTO.setIdticketmessage(ticketMessages.get(j).getIdticketMessage());
				ticketMessageDTO.setIdticket(ticketMessages.get(j).getTicket().getIdticket());
				ticketMessageDTO.setDate(ticketMessages.get(j).getDate());
				ticketMessageDTO.setText(ticketMessages.get(j).getText());
				ticketMessageDTO.setUser(userDTO);
				ticketMessageDTOs.add(ticketMessageDTO);
			}
			
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
			buildingDTO.setName(tickets.get(i).getClassroom().getBuilding().getName());
			
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
			ticketDTO.setTicketmessages(ticketMessageDTOs);
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
			buildingDTO.setName(ticket.getClassroom().getBuilding().getName());
			
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
		
		
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(ticketDTO.getClassroom().getId());
		
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(ticketDTO.getTicketStatus().getIdstatus());
		ticketStatus.setDescription(ticketDTO.getTicketStatus().getDescription());
		
		Ticket ticket = new Ticket();
		int oldstatus = 0;
		try {
			ticket.setIdticket(ticketDTO.getId());
			oldstatus = ticketRepository.getOne(ticketDTO.getId()).getTicketStatus().getIdticketStatus();
		} catch (Exception e) {
			// TODO: handle exception
		}
		ticket.setTitle(ticketDTO.getTitle());
		ticket.setDate(ticketDTO.getDate());
		ticket.setTeacher(teacher);
		ticket.setClassroom(classroom);
		ticket.setTicketStatus(ticketStatus);
		
		try {
			Employee employee = new Employee();
			employee.setIduser(ticketDTO.getEmployee().getIdemployee());
			ticket.setEmployee(employee);
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		
		Ticket newTicket = ticketRepository.save(ticket);
		 
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setIdteacher(newTicket.getTeacher().getIduser());
		teacherDTO.setName(newTicket.getTeacher().getUser().getName());
		teacherDTO.setSurname(newTicket.getTeacher().getUser().getSurname());
		
		
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
		
		newTicketDTO.setTeacher(teacherDTO);
		newTicketDTO.setClassroom(classroomDTO);
		newTicketDTO.setDate(newTicket.getDate());
		
		try {
			EmployeeDTO employeeDTO = new EmployeeDTO();
			employeeDTO.setIdemployee(newTicket.getEmployee().getIduser());
			employeeDTO.setName(newTicket.getEmployee().getUser().getName());
			employeeDTO.setSurname(newTicket.getEmployee().getUser().getSurname());
			
			newTicketDTO.setEmployee(employeeDTO);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(oldstatus != 0) {
			if(oldstatus != newTicket.getTicketStatus().getIdticketStatus()) {
				try {
					fcmService.newTicket(newTicket, "status");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
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
		System.out.println(ticketMessageDTOs.size());
		ticketMessageDTOs.add(ticketMessageDTO);
		ticketDTO.setTicketmessages(ticketMessageDTOs);
		return ticketDTO;
	}
	
	@Transactional
	public TicketMessageDTO saveMessage(TicketMessageDTO ticketMessageDTO) {
		Ticket ticket = new Ticket();
		ticket.setIdticket(ticketMessageDTO.getIdticket());
		
		TicketMessage ticketMessage = new TicketMessage();
		ticketMessage.setIdticketMessage(ticketMessageDTO.getIdticketmessage());
		ticketMessage.setTicket(ticket);
		ticketMessage.setText(ticketMessageDTO.getText());
		ticketMessage.setDate(ticketMessageDTO.getDate());
		
		User user = new User();
		user.setIduser(ticketMessageDTO.getUser().getIduser());
		user.setName(ticketMessageDTO.getUser().getName());
		user.setSurname(ticketMessageDTO.getUser().getSurname());
		
		ticketMessage.setUser(user);
		
		TicketMessage newTicketMessage = ticketMessageRepository.save(ticketMessage);
		
		TicketMessageDTO newTicketMessageDTO = new TicketMessageDTO();
		newTicketMessageDTO.setIdticketmessage(newTicketMessage.getIdticketMessage());
		newTicketMessageDTO.setIdticket(newTicketMessage.getTicket().getIdticket());
		newTicketMessageDTO.setDate(newTicketMessage.getDate());
		newTicketMessageDTO.setText(newTicketMessage.getText());
		
		UserDTO userDTO = new UserDTO();
		userDTO.setIduser(newTicketMessage.getUser().getIduser());
		userDTO.setSurname(newTicketMessage.getUser().getSurname());
		userDTO.setName(newTicketMessage.getUser().getName());

		newTicketMessageDTO.setUser(userDTO);
		Ticket tickett = ticketRepository.getOne(ticketMessageDTO.getIdticket());
		
		if(!userRepository.isTeacher(user.getIduser())) {
			try {
				fcmService.newTicket(tickett, "message");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return newTicketMessageDTO;
	}
}
