package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.domain.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

	@Transactional
	@Modifying
	@Query("DELETE FROM Lesson l WHERE l.typeLesson.idtypeLesson=:idtypelesson")
	public void deleteByTypeLesson(@Param("idtypelesson")int idtypelesson);
	
	@Query("SELECT l FROM Lesson l WHERE l.typeLesson.subject.teacher.iduser=:idteacher ORDER BY l.start DESC")
	public List<Lesson> getAllTeacherLessons(@Param("idteacher")int idteacher);
	
	@Query("SELECT l FROM Lesson l WHERE l.typeLesson.scheduler.degreeCourse.iddegreeCourse=:idcourse AND l.typeLesson.scheduler.term.idterm=:idterm")
	public List<Lesson> getAllLessonsByCourseAndTerm(@Param("idcourse")int idcourse, @Param("idterm")int idterm);
}
