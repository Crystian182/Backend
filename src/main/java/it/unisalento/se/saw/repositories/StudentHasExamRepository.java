package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Exam;
import it.unisalento.se.saw.domain.StudentHasExam;;

@Repository
public interface StudentHasExamRepository extends JpaRepository<StudentHasExam, Integer>  {
	
	@Query("SELECT h FROM StudentHasExam h WHERE h.exam.idexam=:idexam")
	public List<StudentHasExam> getExamStudents(@Param("idexam")int idexam);
	
	@Query("SELECT i FROM Exam e, StudentHasExam i WHERE i.exam.subject.idsubject=:idsubject AND i.student.iduser=:idstudent AND i.result.idresult=2 AND i.exam.idexam=e.idexam")
	public StudentHasExam getPassedExam(@Param("idstudent")int idstudent, @Param("idsubject")int idsubject);
	
	@Query("SELECT i FROM StudentHasExam i WHERE i.student.iduser=:idstudent ORDER BY i.date DESC")
	public List<StudentHasExam> getAllEnrollment(@Param("idstudent")int idstudent);
	
}
