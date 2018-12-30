package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.StudentHasExam;;

@Repository
public interface StudentHasExamRepository extends JpaRepository<StudentHasExam, Integer>  {
	
	@Query("SELECT h FROM StudentHasExam h WHERE h.exam.idexam=:idexam")
	public List<StudentHasExam> getExamStudents(@Param("idexam")int idexam);
	
}
