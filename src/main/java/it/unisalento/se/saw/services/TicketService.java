package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ITicketService;
import it.unisalento.se.saw.domain.ClassroomHasInstruments;
import it.unisalento.se.saw.domain.Instruments;
import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.Ticketmessage;
import it.unisalento.se.saw.exceptions.TicketNotFoundException;
import it.unisalento.se.saw.repositories.TicketRepository;


@Service
public class TicketService implements ITicketService {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Transactional(readOnly = true)
	public List<Ticket> getAll() {
		// TODO Auto-generated method stub
		return ticketRepository.findAll();
	}
	
	@Transactional(rollbackFor=TicketNotFoundException.class)
	public Ticket getById(int id) throws TicketNotFoundException{
		try {
			return ticketRepository.findById(id).get();
		} catch (Exception e) {
			throw new TicketNotFoundException();
		}
		
	}
	
	@Transactional
	public List<Ticketmessage> getMessages(int idticket) {
		return ticketRepository.getMessages(idticket);
	}
	
	@Transactional
	public Ticket save(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketRepository.save(ticket);
	}
	
	
	@Transactional
	public Ticket edit(Ticket ticket) throws TicketNotFoundException{
		try {
			ticketRepository.findById(ticket.getIdticket()).get();
			return ticketRepository.save(ticket);
		} catch (Exception e) {
			throw new TicketNotFoundException();
		}
		
	}

	@Transactional
	public void delete(int id) throws TicketNotFoundException{
		try {
			ticketRepository.deleteById(id);
		} catch (Exception e) {
			throw new TicketNotFoundException();
		}
		
		
	}

	
}
