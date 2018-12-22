package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.TypeDegreeCourse;

@Repository
public interface TypeDegreeCourseRepository extends JpaRepository<TypeDegreeCourse, Integer> {
}
