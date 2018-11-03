package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

	@Query("SELECT e FROM Exam e WHERE e.subject.degreeCourse.iddegreeCourse=:id")
	public List<Exam> findAllByCourse(@Param("id")int id);


	
}
