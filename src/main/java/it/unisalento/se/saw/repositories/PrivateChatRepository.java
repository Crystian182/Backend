package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.PrivateChat;


@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Integer>{
	
	@Query("SELECT c FROM PrivateChat c WHERE c.id.user1Ssn=:ssn OR c.id.user2Ssn=:ssn")
	public List<PrivateChat> getPreviews(@Param("ssn")String ssn);
	
	@Query("SELECT c FROM PrivateChat c WHERE (c.id.user1Ssn=:ssn1 AND c.id.user2Ssn=:ssn2) OR (c.id.user1Ssn=:ssn2 AND c.id.user2Ssn=:ssn1)")
	public PrivateChat getPreviewsWithColleague(@Param("ssn1")String ssn1, @Param("ssn2")String ssn2);

}
