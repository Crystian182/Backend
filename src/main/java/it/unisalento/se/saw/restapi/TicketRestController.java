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
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
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
	
	
	
}
