package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Classroom;

@Repository
public interface AulaRepository extends JpaRepository<Classroom, Integer> {
	@Query("select a from Classroom a where a.name=:name")
	public List<Classroom> getByName(@Param("name")String name);
	
}
