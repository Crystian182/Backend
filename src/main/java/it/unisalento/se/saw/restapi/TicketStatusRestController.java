package it.unisalento.se.saw.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.ITicketStatusService;
import it.unisalento.se.saw.dto.TicketStatusDTO;


@RestController
@RequestMapping("/ticketStatus")
public class TicketStatusRestController {

	@Autowired
	ITicketStatusService ticketStatusService;
	
	public TicketStatusRestController() {
		super();
	}
	
	public TicketStatusRestController(ITicketStatusService ticketStatusService) {
		this.ticketStatusService = ticketStatusService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TicketStatusDTO> getAll(){
		return ticketStatusService.getAll();
	}
}
