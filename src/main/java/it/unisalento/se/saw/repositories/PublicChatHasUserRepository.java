package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Publicchat;
import it.unisalento.se.saw.domain.PublicchatHasUser;

@Repository
public interface PublicChatHasUserRepository extends JpaRepository<PublicchatHasUser, Integer>{
	
	@Query("SELECT c FROM PublicchatHasUser c WHERE c.user.iduser=:userId")
	public List<PublicchatHasUser> getChatIds(@Param("userId")int userId);
	
	@Query("SELECT COUNT(s)>0 FROM PublicchatHasUser s WHERE s.user.iduser=:userid AND s.publicchat.idpublicchat=:chatid")
	public boolean checkSubscribe(@Param("userid")int userid, @Param("chatid")int chatid);

}
