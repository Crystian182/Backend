package it.unisalento.se.saw.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.domain.Message;
import it.unisalento.se.saw.domain.PrivateMessage;

@Repository
public interface SubjectMessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("SELECT m FROM Message m WHERE m.subjectChat.idsubjectChat=:idSubjectChat ORDER BY m.date DESC ")
	public List<Message> getSubjectMessages(@Param("idSubjectChat")int subject);
	
	@Query("SELECT m FROM Message m WHERE m.subjectChat.idsubjectChat=:idsubject ORDER BY m.date DESC")
	public Message getLastMessage(@Param("idsubject")int idsubject);
	
	/*@Query("SELECT m FROM Message m WHERE m.privatechat.idprivatechat=:idChat ORDER BY m.date DESC ")
	public List<Message> getPrivateMessagesFromChatId(@Param("idChat")int chatId);
	
	@Query("SELECT m FROM Message m WHERE m.publicchat.idpublicchat=:idChat ORDER BY m.date DESC ")
	public List<Message> getPublicMessagesFromChatId(@Param("idChat")int chatId);
	
	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Message m WHERE m.publicchat.idpublicchat=:idChat")
    public boolean checkPublicChat(@Param("idChat")int chatId);*/

}
