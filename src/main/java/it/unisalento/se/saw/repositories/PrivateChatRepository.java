package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Privatechat;
import it.unisalento.se.saw.domain.Publicchat;


@Repository
public interface PrivateChatRepository extends JpaRepository<Privatechat, Integer>{
	
	@Query("SELECT c FROM Privatechat c WHERE c.userByUserIduser.iduser=:userId OR c.userByUserIduser1.iduser=:userId")
	public List<Privatechat> findAllByUserId(@Param("userId")int userId);
	
	/*@Query("SELECT c FROM Privatechat c WHERE (c.userByUserIduser.iduser=:idUser AND (c.userByUserIduser1.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR c.userByUserIduser1.surname LIKE LOWER(CONCAT('%', :keyword,'%')) OR c.userByUserIduser1.username LIKE LOWER(CONCAT('%', :keyword,'%')))) OR (c.userByUserIduser1.iduser=:idUser AND (c.userByUserIduser.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR c.userByUserIduser.surname LIKE LOWER(CONCAT('%', :keyword,'%')) OR c.userByUserIduser.username LIKE LOWER(CONCAT('%', :keyword,'%'))))")
	public List<Privatechat> search(@Param("idUser")int idUser, @Param("keyword")String keyword);*/
	
	@Query("SELECT c FROM Privatechat c WHERE (c.userByUserIduser.iduser=:userId AND c.userByUserIduser1.iduser=:userId1) OR (c.userByUserIduser.iduser=:userId1 AND c.userByUserIduser1.iduser=:userId)")
	public Privatechat getChat(@Param("userId")int userId, @Param("userId1")int userId1);

}
