package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Publicchat;

@Repository
public interface PublicChatRepository extends JpaRepository<Publicchat, Integer> {
	
	@Query("SELECT c FROM Publicchat c WHERE c.idpublicchat=:id")
	public Publicchat getChatById(@Param("id")int id);
	
	@Query("SELECT c FROM Publicchat c WHERE c.subjectofstudy.course.idcourse=:idCourse AND c.subjectofstudy.name LIKE LOWER(CONCAT('%', :keyword,'%'))")
	public List<Publicchat> search(@Param("idCourse")int idCourse, @Param("keyword")String keyword);

}
