package it.unisalento.se.saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unisalento.se.saw.domain.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

}
