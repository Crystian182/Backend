package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.domain.ClassroomHasTool;

@Repository
public interface ClassroomHasToolRepository extends JpaRepository<ClassroomHasTool, Integer>{
	
	@Query("SELECT c FROM ClassroomHasTool c WHERE c.id.idclassroom=:idClassroom")
	public List<ClassroomHasTool> getToolByClassroomId(@Param("idClassroom")int idClassroom);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ClassroomHasTool c WHERE c.id.idclassroom=:idClassroom")
	public void removeAllClassroomId(@Param("idClassroom")int idClassroom);

}
