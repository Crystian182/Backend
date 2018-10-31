package it.unisalento.se.saw.Iservices;

import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.UserNotFoundException;

public interface ITeacherService {
	
	public User getById(int id) throws UserNotFoundException;

}
