package it.unisalento.se.saw.restapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.ITicketService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Status;
import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.Ticketmessage;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.InstrumentDTO;
import it.unisalento.se.saw.dto.StatusDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TicketDTO;
import it.unisalento.se.saw.dto.TicketMessageDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.TicketNotFoundException;



@RestController
@RequestMapping("/ticket")
public class TicketRestController {
	
	@Autowired
	ITicketService ticketService;
	
	public TicketRestController() {
		super();
	}
	
	public TicketRestController(ITicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TicketDTO> getAll(){
		List<Ticket> tickets = ticketService.getAll();
		List<TicketDTO> newTicketDTOs = new ArrayList<TicketDTO>();
		for(int i=0; i<tickets.size(); i++) {
			Ticket ticket = tickets.get(i);
			newTicketDTOs.add(this.EntityToDTO(ticket));
		}
		return newTicketDTOs;
	}
	
	
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketDTO getById(@PathVariable("id")int id) throws TicketNotFoundException{
		Ticket ticket = ticketService.getById(id);
		return this.EntityToDTO(ticket);
	}
	
	@PostMapping(value="/edit", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketDTO edit(@RequestBody TicketDTO ticketDTO) throws TicketNotFoundException{
		Ticket ticket = ticketService.edit(this.DTOToEntity(ticketDTO));
		return this.EntityToDTO(ticket);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketDTO save(@RequestBody TicketDTO ticketDTO){
		Ticket ticket = ticketService.save(this.DTOToEntity(ticketDTO));
		//List <Ticketmessage> ticketmessages = ticketService.getMessages(ticketDTO.getId());
		
		return this.EntityToDTO(ticket);
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws TicketNotFoundException{
		ticketService.delete(id);
	}	
	
	
	public TicketDTO EntityToDTO(Ticket ticket) {
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setId(ticket.getUserByUserIduser().getIduser());
		teacherDTO.setName(ticket.getUserByUserIduser().getName());
		teacherDTO.setSurname(ticket.getUserByUserIduser().getSurname());
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(ticket.getClassroom().getBuilding().getIdbuilding());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(ticket.getClassroom().getIdclassroom());
		classroomDTO.setName(ticket.getClassroom().getName());
		classroomDTO.setBuilding(buildingDTO);
		
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setIdstatus(ticket.getStatus().getIdstatus());
		statusDTO.setDescription(ticket.getStatus().getDescription());
		
		
		TicketDTO ticketDTO = new TicketDTO();
		
		ticketDTO.setId(ticket.getIdticket());
		ticketDTO.setTitle(ticket.getTitle());
		ticketDTO.setStatus(statusDTO);
		ticketDTO.setTeacher(teacherDTO);
		ticketDTO.setClassroom(classroomDTO);
		ticketDTO.setText(ticket.getText());
		ticketDTO.setDate(ticket.getDate());

		return ticketDTO;
	}
	
	public Ticket DTOToEntity(TicketDTO ticketDTO) {

		User teacher = new User();
		teacher.setIduser(ticketDTO.getTeacher().getId());
		teacher.setSurname(ticketDTO.getTeacher().getSurname());
		teacher.setName(ticketDTO.getTeacher().getName());
		
		Building building = new Building();
		building.setIdbuilding(ticketDTO.getClassroom().getBuilding().getId());
		building.setName(ticketDTO.getClassroom().getBuilding().getName());
		building.setAddress(ticketDTO.getClassroom().getBuilding().getAddress());
		
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(ticketDTO.getClassroom().getId());
		classroom.setName(ticketDTO.getClassroom().getName());
		classroom.setBuilding(building);
		
		Status status = new Status();
		status.setIdstatus(ticketDTO.getStatus().getIdstatus());
		status.setDescription(ticketDTO.getStatus().getDescription());
		
		Ticket ticket = new Ticket();
		try {
			ticket.setIdticket(ticketDTO.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		ticket.setTitle(ticketDTO.getTitle());
		ticket.setDate(ticketDTO.getDate());
		ticket.setUserByUserIduser(teacher);
		ticket.setText(ticketDTO.getText());
		ticket.setClassroom(classroom);
		ticket.setStatus(status);
		
		return ticket;
	}
	
	public TicketDTO EntityToDTO2(Ticket ticket, List <Ticketmessage> ticketmessages) {
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setId(ticket.getUserByUserIduser().getIduser());
		teacherDTO.setName(ticket.getUserByUserIduser().getName());
		teacherDTO.setSurname(ticket.getUserByUserIduser().getSurname());
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(ticket.getClassroom().getBuilding().getIdbuilding());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(ticket.getClassroom().getIdclassroom());
		classroomDTO.setName(ticket.getClassroom().getName());
		classroomDTO.setBuilding(buildingDTO);
		
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setIdstatus(ticket.getStatus().getIdstatus());
		statusDTO.setDescription(ticket.getStatus().getDescription());
		
		TicketMessageDTO ticketMessageDTO = new TicketMessageDTO();
		List<TicketMessageDTO> ticketMessageDTOs = new ArrayList<TicketMessageDTO>();
			for (int i=0; i<ticketmessages.size(); i++) {
				UserDTO user = new UserDTO();
				user.setId(ticketmessages.get(i).getUser().getIduser());
				user.setName(ticketmessages.get(i).getUser().getName());
				user.setSurname(ticketmessages.get(i).getUser().getSurname());
				ticketMessageDTO.setIdticketmessage(ticketmessages.get(i).getIdticketmessage());
				ticketMessageDTO.setIdticket(ticketmessages.get(i).getTicket().getIdticket());
				ticketMessageDTO.setText(ticketmessages.get(i).getText());
				ticketMessageDTO.setDate(ticketmessages.get(i).getDate());
				ticketMessageDTO.setUser(user);
				ticketMessageDTOs.add(ticketMessageDTO);
			}
		
		
		TicketDTO ticketDTO = new TicketDTO();
		
		ticketDTO.setId(ticket.getIdticket());
		ticketDTO.setTitle(ticket.getTitle());
		ticketDTO.setStatus(statusDTO);
		ticketDTO.setTeacher(teacherDTO);
		ticketDTO.setClassroom(classroomDTO);
		ticketDTO.setText(ticket.getText());
		ticketDTO.setDate(ticket.getDate());
		ticketDTO.setTicketmessages(ticketMessageDTOs);

		return ticketDTO;
	}
	
	
	
}
