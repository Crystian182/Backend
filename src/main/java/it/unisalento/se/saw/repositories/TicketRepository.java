package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.TicketMessage;
import it.unisalento.se.saw.dto.TicketMessageDTO;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	@Query("SELECT m FROM TicketMessage m WHERE m.ticket.idticket=:idticket ORDER BY m.date DESC ")
	public List<TicketMessage> getMessages(@Param("idticket")int idticket);
	
	@Query("SELECT t FROM Ticket t WHERE t.teacher.iduser=:idteacher ORDER BY t.date DESC ")
	public List<Ticket> getAllTeacherTickets(@Param("idteacher")int idteacher);
	
	@Query("SELECT m FROM TicketMessage m WHERE m.ticket.idticket=:idticket ORDER BY m.date ASC ")
	public List<TicketMessage> getMessagesASC(@Param("idticket")int idticket);
	
	@Query("SELECT t FROM Ticket t WHERE t.classroom.idclassroom=:idclassroom ORDER BY t.date DESC ")
	public List<Ticket> getAllClassroomTickets(@Param("idclassroom")int idclassroom);
}
