package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.unisalento.se.saw.domain.DegreeCourse;

@Repository
public interface DegreeCourseRepository extends JpaRepository<DegreeCourse, Integer> {

	
	@Query("SELECT DISTINCT s.degreeCourse FROM Subject s WHERE s.teacher.iduser=:iduser")
	public List<DegreeCourse> getCourseTeacher(@Param("iduser")int iduser);
}
