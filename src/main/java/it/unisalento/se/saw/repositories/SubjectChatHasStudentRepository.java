package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.SubjectChatHasStudent;

@Repository
public interface SubjectChatHasStudentRepository extends JpaRepository<SubjectChatHasStudent, Integer>{
	
	@Query("SELECT c FROM SubjectChatHasStudent c WHERE c.id.studentSn=:ssn")
	public List<SubjectChatHasStudent> getPreviews(@Param("ssn")String ssn);
	
	@Query("SELECT c FROM SubjectChatHasStudent c WHERE c.id.studentSn=:ssn AND c.subjectChat.subject.name LIKE LOWER(CONCAT('%', :keyword,'%'))")
	public List<SubjectChatHasStudent> search(@Param("ssn")String ssn, @Param("keyword")String keyword);
	
	@Query("SELECT COUNT(s)>0 FROM PublicchatHasUser s WHERE s.user.iduser=:userid AND s.publicchat.idpublicchat=:chatid")
	public boolean checkSubscribe(@Param("userid")int userid, @Param("chatid")int chatid);

}
