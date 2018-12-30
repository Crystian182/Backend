package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.FeedbackFile;
import it.unisalento.se.saw.dto.FeedbackDTO;

@Repository
public interface FeedbackFileRepository extends JpaRepository<FeedbackFile, Integer> {
	
	@Query("SELECT AVG(f.feedback.stars) FROM FeedbackFile f WHERE f.fileLesson.file.idfile=:idfile")
	public Double getStars(@Param("idfile")int idfile);
	
	@Query("SELECT f FROM FeedbackFile f WHERE f.fileLesson.file.idfile=:idfile")
	public List<FeedbackFile> getFeedbackFile(@Param("idfile")int idfile);

}
