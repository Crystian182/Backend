package it.unisalento.se.saw.Iservices;
import java.util.List;

import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.Ticketmessage;
import it.unisalento.se.saw.exceptions.TicketNotFoundException;


public interface ITicketService {
	public List<Ticket> getAll();
	public Ticket getById(int id) throws TicketNotFoundException;
	public Ticket edit(Ticket ticket) throws TicketNotFoundException;
	public Ticket save(Ticket ticket);
	public void delete(int id) throws TicketNotFoundException;
	public List<Ticketmessage> getMessages(int idticket);

}
