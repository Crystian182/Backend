package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

	@Query("SELECT e FROM Exam e WHERE e.subject.degreeCourse.iddegreeCourse=:idcourse AND e.subject.term.idterm=:idterm")
	public List<Exam> findAllByCourseAndTerm(@Param("idcourse")int idcourse, @Param("idterm")int idterm);

	@Query("SELECT e FROM Exam e WHERE e.subject.teacher.iduser=:idteacher AND e.examStatus.idexamStatus=1 ORDER BY e.date DESC")
	public List<Exam> getAllAvailableByTeacher(@Param("idteacher")int idteacher);
	
	@Modifying
	@Query("UPDATE Exam e set e.examStatus.idexamStatus=2")
	public void closeExam(@Param("idexam")int idexam);

	
}
