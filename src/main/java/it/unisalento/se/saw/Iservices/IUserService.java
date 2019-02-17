package it.unisalento.se.saw.Iservices;

import java.io.IOException;
import java.util.List;

import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.StudentHasDegreeCourseDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongCredentialsException;


public interface IUserService {
	
	public List<User> getAll();
	public UserDTO save(UserDTO user) throws IOException;
	public UserDTO login(String email, String token) throws UserNotFoundException;
	public User getUser(int idUser) throws UserNotFoundException;
	public UserDTO getById(int idUser) throws UserNotFoundException;
	public List<TeacherDTO> getTeachers() throws UserNotFoundException;
	public TeacherDTO getTeacherById(int id) throws UserNotFoundException;
	//public List<User> getColleagues(User user, String keyword) throws UserNotFoundException;
	public StudentHasDegreeCourseDTO getInfo(int idstudent) throws UserNotFoundException;

}
