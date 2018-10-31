package it.unisalento.se.saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer>{

	@Query("select s from Status s where s.idstatus=:id")
	public Status getById(@Param("id")int id);

}
