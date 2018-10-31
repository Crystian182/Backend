package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
/*
	@Query("select u from User u where u.userType.iduserType=1 and u.corsoDiStudio.idcorsoDiStudio=:idCorsoDiStudio")
	public List<User> getUserByCorsoDiStudioId(@Param("idCorsoDiStudio")int idCorsoDiStudio);*/
	
	@Query("SELECT u FROM User u WHERE u.email=:email")
	public User checkUser(@Param("email")String email);
	
	@Query("SELECT u FROM User u WHERE u.iduser!=:idUser AND u.course.idcourse=:idCourse AND (u.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR u.surname LIKE LOWER(CONCAT('%', :keyword,'%')) OR u.username LIKE LOWER(CONCAT('%', :keyword,'%')))")
	public List<User> getColleagues(@Param("idCourse")int idCourse, @Param("idUser")int idUser, @Param("keyword")String keyword);
	
	
}
