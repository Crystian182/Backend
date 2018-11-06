package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.dto.TicketMessageDTO;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	@Query("SELECT m FROM TicketMessage m WHERE m.ticket.idticket=:idticket ORDER BY m.date DESC ")
	public List<TicketMessageDTO> getMessages(@Param("idticket")int idticket);
}
