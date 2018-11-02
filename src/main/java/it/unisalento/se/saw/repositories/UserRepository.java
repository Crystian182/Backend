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
	
	@Query("SELECT COUNT(s)>0 FROM Student s WHERE s.user.ssn=:ssn")
	public boolean isStudent(@Param("ssn")String ssn);
	
	@Query("SELECT COUNT(t)>0 FROM Teacher t WHERE t.ssn=:ssn")
	public boolean isTeacher(@Param("ssn")String ssn);
	
	@Query("SELECT COUNT(e)>0 FROM Employee e WHERE e.ssn=:ssn")
	public boolean isEmployee(@Param("ssn")String ssn);
	
	@Query("SELECT s FROM Student s WHERE s.user.ssn=:ssn")
	public Student getStudent(@Param("ssn")String ssn);
	
	@Query("SELECT DISTINCT t FROM User u, Teacher t, Subject s WHERE t.ssn!=:ssn AND t.ssn=u.ssn AND t.ssn=s.teacher.ssn AND s.degreeCourse.iddegreeCourse=:idCourse")
	public List<Teacher> getTeacherColleagues(@Param("idCourse")int idCourse, @Param("ssn")String ssn);
	
	@Query("SELECT DISTINCT s FROM Student s, Enrollment e WHERE s.user.ssn!=:ssn AND s.user.ssn=e.student.user.ssn AND e.degreeCourse.iddegreeCourse=:idCourse")
	public List<Student> getStudentColleagues(@Param("idCourse")int idCourse, @Param("ssn")String ssn);
	
	@Query("SELECT DISTINCT t FROM User u, Teacher t, Subject s WHERE t.ssn!=:ssn AND t.ssn=u.ssn AND t.ssn=s.teacher.ssn AND s.degreeCourse.iddegreeCourse=:idCourse AND (u.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR u.surname LIKE LOWER(CONCAT('%', :keyword,'%')))")
	public List<Teacher> searchTeacherColleagues(@Param("idCourse")int idCourse, @Param("ssn")String ssn, @Param("keyword")String keyword);
	
	@Query("SELECT DISTINCT s FROM Student s, Enrollment e WHERE s.user.ssn!=:ssn AND s.user.ssn=e.student.user.ssn AND e.degreeCourse.iddegreeCourse=:idCourse AND (s.user.name LIKE LOWER(CONCAT('%', :keyword,'%')) OR s.user.surname LIKE LOWER(CONCAT('%', :keyword,'%')))")
	public List<Student> searchStudentColleagues(@Param("idCourse")int idCourse, @Param("ssn")String ssn, @Param("keyword")String keyword);
	
	
}
