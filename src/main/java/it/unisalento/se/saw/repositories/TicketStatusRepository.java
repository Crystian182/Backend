package it.unisalento.se.saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.TicketStatus;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer>{

	@Query("select s from TicketStatus s where s.idticketStatus=:id")
	public TicketStatus getById(@Param("id")int id);

}
