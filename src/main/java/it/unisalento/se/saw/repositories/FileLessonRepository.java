package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.File;
import it.unisalento.se.saw.domain.FileLesson;

@Repository
public interface FileLessonRepository extends JpaRepository<FileLesson, Integer>{
	
	@Query("SELECT f.file FROM FileLesson f WHERE f.lesson.idlesson=:idlesson")
	public List<File> getLessonFiles(@Param("idlesson")int idlesson);
	
	@Query("SELECT f FROM FileLesson f, Lesson l, Subject s, StudentHasDegreeCourse e WHERE f.lesson.idlesson=l.idlesson AND l.typeLesson.subject.idsubject=s.idsubject AND s.degreeCourse.iddegreeCourse=e.degreeCourse.iddegreeCourse AND e.student.iduser=:idstudent ORDER BY f.date DESC")
	public List<FileLesson> getLastFiles(@Param("idstudent")int idstudent);
	
	@Query("SELECT f FROM FileLesson f WHERE f.lesson.typeLesson.subject.teacher.iduser=:iduser ORDER BY f.date DESC")
	public List<FileLesson> getLastFilesTeacher(@Param("iduser")int iduser);
	
	@Query("SELECT f FROM FileLesson f WHERE f.lesson.typeLesson.subject.idsubject=:idsubject ORDER BY f.date DESC")
	public List<FileLesson> getSubjectFiles(@Param("idsubject")int idsubject);
	
	@Query("SELECT f FROM FileLesson f WHERE f.file.idfile=:idfile")
	public Integer getIdLessonFromIdFile(@Param("idfile")int idfile);

}
