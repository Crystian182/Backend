package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.AcademicYear;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Integer> {

	@Query("SELECT a FROM AcademicYear a, DegreeCourse d WHERE a.year<=d.academicYear.year+d.typeDegreeCourse.courseType.duration AND d.iddegreeCourse=:idcourse")
	public List<AcademicYear> getAllYearsByCourse(@Param("idcourse")int idcourse);
}
