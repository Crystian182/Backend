package it.unisalento.se.saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Subjectofstudy;

@Repository
public interface SubjectOfStudyRepository extends JpaRepository<Subjectofstudy, Integer> {

}
