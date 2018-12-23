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

}
