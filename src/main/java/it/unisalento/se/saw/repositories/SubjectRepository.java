package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
	
	@Query("SELECT DISTINCT s.degreeCourse FROM Subject s WHERE s.teacher.ssn=:ssn")
	public List<DegreeCourse> getCourseTeacher(@Param("ssn")String ssn);

}
