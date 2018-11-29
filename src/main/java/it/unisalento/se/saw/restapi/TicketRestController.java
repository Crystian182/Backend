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
import it.unisalento.se.saw.dto.TicketDTO;
import it.unisalento.se.saw.dto.TicketMessageDTO;
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
	

	@GetMapping(value="/getMessages/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TicketMessageDTO> getMessages(@PathVariable("id")int id) throws TicketNotFoundException{
		return ticketService.getMessages(id);
	}
	
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketDTO save(@RequestBody TicketDTO ticketDTO){
		return ticketService.save(ticketDTO);
	
	}
	
	@PostMapping(value="/savestaff", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketDTO savestaff(@RequestBody TicketDTO ticketDTO, TicketMessageDTO ticketMessageDTO){
		return ticketService.saveMessages(ticketDTO, ticketMessageDTO);
		
	}
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws TicketNotFoundException{
		ticketService.delete(id);
	}	
	
	@PostMapping(value="/savemessage", produces=MediaType.APPLICATION_JSON_VALUE)
	public TicketMessageDTO savemessage(@RequestBody TicketMessageDTO ticketMessageDTO){
		return ticketService.saveMessage(ticketMessageDTO);
		
	}
	
}
