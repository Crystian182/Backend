package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}
