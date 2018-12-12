package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Term;

@Repository
public interface TermRepository extends JpaRepository<Term, Integer> {

	@Query("SELECT t FROM Term t WHERE t.academicYear.idacademicYear=:id")
	public List<Term> getByAcademicYear(@Param("id")int id);
}
