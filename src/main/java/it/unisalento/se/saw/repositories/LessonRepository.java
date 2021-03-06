package it.unisalento.se.saw.repositories;

import java.util.Date;
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
	
	@Query("SELECT l FROM Lesson l, TypeLesson t, Scheduler s, DegreeCourse d, StudentHasDegreeCourse e WHERE DATE(l.start)=CURDATE() AND DATE(l.end)=CURDATE() AND l.typeLesson.idtypeLesson=t.idtypeLesson AND t.scheduler.idscheduler=s.idscheduler AND s.degreeCourse.iddegreeCourse=d.iddegreeCourse AND d.iddegreeCourse=e.degreeCourse.iddegreeCourse AND e.student.iduser=:iduser")
	public List<Lesson> getTodayLessons(@Param("iduser")int iduser);
	
	@Query("SELECT l FROM Lesson l, TypeLesson t, Scheduler s, DegreeCourse d, StudentHasDegreeCourse e WHERE DATE(l.start)=CURDATE() AND DATE(l.end)=CURDATE() AND l.typeLesson.subject.teacher.iduser=:iduser")
	public List<Lesson> getTeacherTodayLessons(@Param("iduser")int iduser);
	
	@Query("SELECT l FROM Lesson l WHERE l.typeLesson.scheduler.degreeCourse.iddegreeCourse=:idcourse AND l.typeLesson.scheduler.term.idterm=:idterm AND l.typeLesson.subject.idsubject=:idsubject AND DATE(l.start)>=:from AND DATE(l.end)<=:to")
	public List<Lesson> searchLessons(@Param("idcourse")int idcourse, @Param("idterm")int idterm, @Param("idsubject")int idsubject, @Param("from")Date from, @Param("to")Date to);
	
	@Query("SELECT l FROM Lesson l WHERE l.typeLesson.subject.idsubject=:idsubject AND DATE(l.start)>=:from AND DATE(l.end)<=:to")
	public List<Lesson> searchTeacherLessons(@Param("idsubject")int idsubject, @Param("from")Date from, @Param("to")Date to);
}
