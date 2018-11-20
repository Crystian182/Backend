package it.unisalento.se.saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.StudentHasExam;;

@Repository
public interface StudentHasExamRepository extends JpaRepository<StudentHasExam, Integer>  {

}
