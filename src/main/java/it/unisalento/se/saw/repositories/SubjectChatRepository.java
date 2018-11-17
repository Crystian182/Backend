package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectChatRepository extends JpaRepository<Object, Integer> {
	
	/*@Query("SELECT c FROM SubjectChat c WHERE c.subject.teacher.ssn=:ssn")
	public List<SubjectChat> getTeacherPreviews(@Param("ssn")String ssn);
	
	@Query("SELECT c FROM SubjectChat c WHERE c.subject.teacher.ssn=:ssn AND c.subject.name LIKE LOWER(CONCAT('%', :keyword,'%'))")
	public List<SubjectChat> search(@Param("ssn")String ssn, @Param("keyword")String keyword);	*/
	

}
