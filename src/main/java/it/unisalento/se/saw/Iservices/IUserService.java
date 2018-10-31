package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongPasswordException;
import it.unisalento.se.saw.models.Login;


public interface IUserService {
	
	public List<User> getAll();
	public User save(User user);
	public User checkUser(Login login) throws UserNotFoundException, WrongPasswordException;
	public User getUser(int idUser) throws UserNotFoundException;
	public List<User> getColleagues(User user, String keyword) throws UserNotFoundException;

}
