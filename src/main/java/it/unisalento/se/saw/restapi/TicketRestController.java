package it.unisalento.se.saw.restapi;

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
<<<<<<< HEAD
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Status;
import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.Ticketmessage;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.dto.StatusDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
=======
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098
import it.unisalento.se.saw.dto.TicketDTO;
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
		return ticketService.getAll();
	}
	
	
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketDTO getById(@PathVariable("id")int id) throws TicketNotFoundException{
		return ticketService.getById(id);
	}
	
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketDTO save(@RequestBody TicketDTO ticketDTO){
		return ticketService.save(ticketDTO);
	
	}
	
	/*@PostMapping(value="/staff/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketDTO savestaff(@RequestBody TicketDTO ticketDTO){
		
		Ticket ticket = ticketService.save(this.DTOToEntity2(ticketDTO));
		
		List <Ticketmessage> ticketmessages = ticketService.saveMessages(this.DTOToEntityMessage(ticketDTO.getTicketmessages()));
		System.out.println("lista = " + ticketmessages.size());
		return this.EntityToDTO2(ticket, ticketmessages);
	}
	*/
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws TicketNotFoundException{
		ticketService.delete(id);
	}	
	
	
	
	
/*	public TicketDTO EntityToDTO2(Ticket ticket, List <Ticketmessage> ticketmessages) {
		
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
		
		TicketStatusDTO statusDTO = new TicketStatusDTO();
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
	*/
	
	
}
