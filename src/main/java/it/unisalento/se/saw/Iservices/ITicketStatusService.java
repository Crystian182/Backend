package it.unisalento.se.saw.Iservices;

import java.util.List;
import it.unisalento.se.saw.dto.TicketStatusDTO;

public interface ITicketStatusService {
	public List<TicketStatusDTO> getAll();

}
