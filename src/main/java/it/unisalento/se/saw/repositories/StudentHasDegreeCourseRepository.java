package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.StudentHasDegreeCourse;


@Repository
public interface StudentHasDegreeCourseRepository extends JpaRepository<StudentHasDegreeCourse, Integer>  {
	
	@Query("SELECT h FROM StudentHasDegreeCourse h WHERE h.id.studentIduser=:idStudent")
	public StudentHasDegreeCourse getStudentCourse(@Param("idStudent")int idStudent);

}
