package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
/*
	@Query("select u from User u where u.userType.iduserType=1 and u.corsoDiStudio.idcorsoDiStudio=:idCorsoDiStudio")
	public List<User> getUserByCorsoDiStudioId(@Param("idCorsoDiStudio")int idCorsoDiStudio);*/
	
	@Query("SELECT u FROM User u WHERE u.email=:email")
	public User checkUser(@Param("email")String email);
	
	@Query("SELECT COUNT(s)>0 FROM Student s WHERE s.user.iduser=:iduser")
	public boolean isStudent(@Param("iduser")int iduser);
	
	@Query("SELECT COUNT(t)>0 FROM Teacher t WHERE t.iduser=:iduser")
	public boolean isTeacher(@Param("iduser")int iduser);
	
	@Query("SELECT COUNT(e)>0 FROM Employee e WHERE e.iduser=:iduser")
	public boolean isEmployee(@Param("iduser")int iduser);
	
	@Query("SELECT s FROM Student s WHERE s.id.iduser=:iduser")
	public Student getStudent(@Param("iduser")int iduser);
	
	@Query("SELECT DISTINCT t FROM User u, Teacher t, Subject s WHERE t.iduser!=:iduser AND t.iduser=u.iduser AND t.iduser=s.teacher.iduser AND s.degreeCourse.iddegreeCourse=:idCourse")
	public List<Teacher> getTeacherColleagues(@Param("idCourse")int idCourse, @Param("iduser")int iduser);
	
	/*@Query("SELECT DISTINCT s FROM Student s, Enrollment e WHERE s.user.iduser!=:iduser AND s.user.iduser=e.student.user.iduser AND e.degreeCourse.iddegreeCourse=:idCourse")
	public List<Student> getStudentColleagues(@Param("idCourse")int idCourse, @Param("iduser")int iduser);*/
	
	@Query("SELECT DISTINCT t FROM User u, Teacher t, Subject s WHERE t.iduser!=:iduser AND t.iduser=u.iduser AND t.iduser=s.teacher.iduser AND s.degreeCourse.iddegreeCourse=:idCourse AND (u.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR u.surname LIKE LOWER(CONCAT('%', :keyword,'%')))")
	public List<Teacher> searchTeacherColleagues(@Param("idCourse")int idCourse, @Param("iduser")int iduser, @Param("keyword")String keyword);
	
	/*@Query("SELECT DISTINCT s FROM Student s, Enrollment e WHERE s.user.iduser!=:iduser AND s.user.iduser=e.student.user.iduser AND e.degreeCourse.iddegreeCourse=:idCourse AND (s.user.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR s.user.surname LIKE LOWER(CONCAT('%', :keyword,'%')))")
	public List<Student> searchStudentColleagues(@Param("idCourse")int idCourse, @Param("iduser")int iduser, @Param("keyword")String keyword);
	*/	
	
	@Query("SELECT t FROM Teacher t WHERE t.iduser!=:iduser AND (t.user.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR t.user.surname LIKE LOWER(CONCAT('%', :keyword,'%')) OR CONCAT(t.user.name,' ', t.user.surname) LIKE LOWER(CONCAT('%', :keyword,'%')) OR CONCAT(t.user.surname,' ', t.user.name) LIKE LOWER(CONCAT('%', :keyword,'%')))")
	public List<Teacher> searchTeachers(@Param("iduser")int iduser, @Param("keyword")String keyword);
	
	@Query("SELECT s FROM Student s WHERE s.user.iduser!=:iduser AND (s.user.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR s.user.surname LIKE LOWER(CONCAT('%', :keyword,'%')) OR CONCAT(s.user.name,' ', s.user.surname) LIKE LOWER(CONCAT('%', :keyword,'%')) OR CONCAT(s.user.surname,' ', s.user.name) LIKE LOWER(CONCAT('%', :keyword,'%')))")
	public List<Student> searchStudents(@Param("iduser")int iduser, @Param("keyword")String keyword);
	
}
