package it.unisalento.se.saw.Iservices;
import java.util.List;

import it.unisalento.se.saw.domain.TicketMessage;
import it.unisalento.se.saw.dto.TicketDTO;
import it.unisalento.se.saw.exceptions.TicketNotFoundException;


public interface ITicketService {
	public List<TicketDTO> getAll();
	public TicketDTO getById(int id) throws TicketNotFoundException;
	public TicketDTO save(TicketDTO ticketDTO);
	public void delete(int id) throws TicketNotFoundException;
	public List<TicketMessage> getMessages(int idticket);

}
