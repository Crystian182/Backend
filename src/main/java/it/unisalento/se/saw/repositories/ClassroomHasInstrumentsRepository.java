package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Instruments;
import it.unisalento.se.saw.domain.ClassroomHasInstruments;

@Repository
public interface ClassroomHasInstrumentsRepository extends JpaRepository<ClassroomHasInstruments, Integer>{
	
	@Query("SELECT i FROM ClassroomHasInstruments i WHERE i.classroom.idclassroom=:idClassroom")
	public List<ClassroomHasInstruments> findInstrumentsByClassroom(@Param("idClassroom")int idClassroom);

}
