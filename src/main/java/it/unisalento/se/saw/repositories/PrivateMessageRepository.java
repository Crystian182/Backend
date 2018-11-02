package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.PrivateMessage;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Integer> {
	
	@Query("SELECT m FROM PrivateMessage m WHERE (m.privateChat.id.user1Ssn=:ssn1 AND m.privateChat.id.user2Ssn=:ssn2) OR (m.privateChat.id.user1Ssn=:ssn2 AND m.privateChat.id.user2Ssn=:ssn1) ORDER BY m.date DESC")
	public PrivateMessage getLastMessage(@Param("ssn1")String ssn1, @Param("ssn2")String ssn2);
	
	@Query("SELECT m FROM PrivateMessage m WHERE (m.privateChat.id.user1Ssn=:ssn1 AND m.privateChat.id.user2Ssn=:ssn2) OR (m.privateChat.id.user1Ssn=:ssn2 AND m.privateChat.id.user2Ssn=:ssn1) ORDER BY m.date DESC")
	public List<PrivateMessage> getChat(@Param("ssn1")String ssn1, @Param("ssn2")String ssn2);

}
