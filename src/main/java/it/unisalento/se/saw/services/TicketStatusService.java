package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ITicketStatusService;
import it.unisalento.se.saw.domain.TicketStatus;
import it.unisalento.se.saw.dto.TicketStatusDTO;
import it.unisalento.se.saw.repositories.TicketStatusRepository;

@Service
public class TicketStatusService implements ITicketStatusService{

	@Autowired
	TicketStatusRepository statusRepository;
	
	@Transactional(readOnly = true)
	public List<TicketStatusDTO> getAll() {
		List<TicketStatus> ticketStati =  statusRepository.findAll();
		List<TicketStatusDTO> ticketStatusDTOs = new ArrayList<TicketStatusDTO>();
		for(int i=0; i<ticketStati.size(); i++) {
			TicketStatusDTO ticketStatusDTO = new TicketStatusDTO();
			ticketStatusDTO.setIdstatus(ticketStati.get(i).getIdticketStatus());
			ticketStatusDTO.setDescription(ticketStati.get(i).getDescription());
			ticketStatusDTOs.add(ticketStatusDTO);
		}
		return ticketStatusDTOs;
	}
	

}
