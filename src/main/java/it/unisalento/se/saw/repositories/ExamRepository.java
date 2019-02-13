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

	@Query("SELECT e FROM Exam e WHERE e.subject.teacher.iduser=:idteacher ORDER BY e.date DESC")
	public List<Exam> getAllAvailableByTeacher(@Param("idteacher")int idteacher);
	
	@Modifying
	@Query("UPDATE Exam e set e.examStatus.idexamStatus=2")
	public void closeExam(@Param("idexam")int idexam);
	
	@Query("SELECT e FROM Exam e, StudentHasDegreeCourse i WHERE i.student.iduser=:idstudent AND i.degreeCourse.iddegreeCourse=e.subject.degreeCourse.iddegreeCourse AND e.examStatus.idexamStatus=1 AND CURDATE()<=DATE(e.date)-1 AND e.subject.typeSubject.idtypeSubject NOT IN (SELECT r.exam.subject.typeSubject.idtypeSubject FROM StudentHasExam r WHERE r.student.iduser=:idstudent AND r.result.idresult=2) AND e.idexam NOT IN (SELECT y.exam.idexam FROM StudentHasExam y WHERE y.student.iduser=:idstudent AND y.result.idresult=1) ORDER BY e.date DESC")
	public List<Exam> getAllAvailableByStudent(@Param("idstudent")int idstudent);
	
}
