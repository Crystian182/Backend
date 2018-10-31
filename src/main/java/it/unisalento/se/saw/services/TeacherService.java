package it.unisalento.se.saw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ITeacherService;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.repositories.TeacherRepository;

@Service
public class TeacherService implements ITeacherService {
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public User getById(int id) throws UserNotFoundException {
		
		try {
			return teacherRepository.findById(id).get();
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
	}

}
