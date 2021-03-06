package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.TypeLesson;

@Repository
public interface TypeLessonRepository extends JpaRepository<TypeLesson, Integer>{

	@Query("SELECT t FROM TypeLesson t WHERE t.scheduler.idscheduler IN (SELECT s.idscheduler FROM Scheduler s, Term m WHERE s.term.idterm=m.idterm AND m.idterm=:idterm AND s.degreeCourse.iddegreeCourse=:idCourse AND NOW()>=m.start AND NOW()<=m.end)")
	public List<TypeLesson> getCurrentSchedulerByIDCourse(@Param("idCourse")int idCourse, @Param("idterm")int idterm);
	
	@Query("SELECT t FROM TypeLesson t WHERE t.scheduler.idscheduler=:idScheduler")
	public List<TypeLesson> getTypeLessonsOfScheduler(@Param("idScheduler")int idScheduler);
	
	@Query("SELECT t FROM TypeLesson t, Subject s WHERE s.teacher.iduser=:idteacher AND s.idsubject=t.subject.idsubject AND NOW()>=t.scheduler.term.start AND NOW()<=t.scheduler.term.end")
	public List<TypeLesson> getCurrentSchedulerTeacher(@Param("idteacher")int idteacher);
	
}
