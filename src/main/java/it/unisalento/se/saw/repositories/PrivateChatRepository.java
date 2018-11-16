package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.PrivateChat;


@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Integer>{
	
	@Query("SELECT c FROM PrivateChat c WHERE c.userByUser1Iduser=:iduser OR c.userByUser2Iduser=:iduser")
	public List<PrivateChat> getPreviews(@Param("iduser")int iduser);
	
	@Query("SELECT c FROM PrivateChat c WHERE (c.userByUser1Iduser=:iduser1 AND c.userByUser2Iduser=:iduser2) OR (c.userByUser1Iduser=:iduser2 AND c.userByUser2Iduser=:iduser1)")
	public PrivateChat getPreviewsWithColleague(@Param("iduser1")int iduser1, @Param("iduser2")int iduser2);

}
