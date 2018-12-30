package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.FeedbackLesson;

@Repository
public interface FeedbackLessonRepository extends JpaRepository<FeedbackLesson, Integer> {
	
	@Query("SELECT f FROM FeedbackLesson f WHERE f.lesson.idlesson=:idlesson")
	public List<FeedbackLesson> getFeedbackLesson(@Param("idlesson")int idlesson);

}
