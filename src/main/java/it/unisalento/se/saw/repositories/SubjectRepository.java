package it.unisalento.se.saw.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
	
	@Query("SELECT s FROM Subject s WHERE s.degreeCourse.iddegreeCourse=:idCourse")
	public List<Subject> getByIdCourse(@Param("idCourse")int idCourse);

	@Query("SELECT s FROM Subject s WHERE s.degreeCourse.iddegreeCourse=:idcourse")
	public List<Subject> getSubjectsByIdDegreeCourse(@Param("idcourse")int idcourse);

	@Query("SELECT s FROM Subject s WHERE s.teacher.iduser=:id")
	public List<Subject> getByIdTeacher(@Param("id")int id);

}
