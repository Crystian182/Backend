package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.DegreeCourse;
//import it.unisalento.se.saw.domain.Enrollment;

/*@Repository
public interface EnrollmentRepository extends JpaRepository<Object, Integer>{
	
	@Query("SELECT DISTINCT e.degreeCourse FROM Enrollment e WHERE e.student.user.ssn=:ssn")
	public List<DegreeCourse> getCourseStudent(@Param("ssn")String ssn);

}*/
