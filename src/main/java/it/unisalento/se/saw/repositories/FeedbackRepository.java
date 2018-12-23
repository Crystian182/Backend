package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Feedback;
import it.unisalento.se.saw.domain.FeedbackFile;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer>{
	
	@Query("SELECT AVG(f.stars) FROM Feedback f WHERE f.file.idfile=:idfile")
	public Double getStars(@Param("idfile")int idfile);

}
