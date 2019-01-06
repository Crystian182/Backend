package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.LoginDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongCredentialsException;
import it.unisalento.se.saw.models.Login;
import it.unisalento.se.saw.repositories.TeacherRepository;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class UserService implements IUserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TeacherRepository teacherRepository;
	
	
	@Transactional(readOnly=true)
	public List<User> getAll(){
		return userRepository.findAll();	
	}
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public UserDTO getById(int id) throws UserNotFoundException {
		try {
			User user = userRepository.getOne(id);
			UserDTO userDTO = new UserDTO();
			userDTO.setIduser(user.getIduser());
			userDTO.setName(user.getName());
			userDTO.setSurname(user.getSurname());
			userDTO.setDateBirth(user.getDateBirth());
			userDTO.setCitizenship(user.getCitizenship());
			userDTO.setDomicile(user.getDomicile());
			userDTO.setPhone(user.getPhone());
			userDTO.setResidence(user.getResidence());
			userDTO.setSex(user.getSex());
			userDTO.setSsn(user.getSsn());
			return userDTO;
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
	}
	
	@Transactional
	public List<TeacherDTO> getTeachers() throws UserNotFoundException {
		List<Teacher> teachers = teacherRepository.findAll();
		List<TeacherDTO> teacherDTOs = new ArrayList<TeacherDTO>();
		for(int i=0; i<teachers.size(); i++) {
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(teachers.get(i).getIduser());
			teacherDTO.setSurname(teachers.get(i).getUser().getSurname());
			teacherDTO.setName(teachers.get(i).getUser().getName());
			teacherDTO.setDateBirth(teachers.get(i).getUser().getDateBirth());
			teacherDTO.setDomicile(teachers.get(i).getUser().getDomicile());
			teacherDTO.setPlaceBirth(teachers.get(i).getUser().getPlaceBirth());
			teacherDTO.setResidence(teachers.get(i).getUser().getResidence());
			teacherDTOs.add(teacherDTO);
		}
		return teacherDTOs;
	}
	
	public TeacherDTO getTeacherById(int id) throws UserNotFoundException {
		try {
			Teacher teacher = teacherRepository.getOne(id);
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(teacher.getIduser());
			teacherDTO.setName(teacher.getUser().getName());
			teacherDTO.setSurname(teacher.getUser().getSurname());
			teacherDTO.setDateBirth(teacher.getUser().getDateBirth());
			teacherDTO.setDomicile(teacher.getUser().getDomicile());
			teacherDTO.setPlaceBirth(teacher.getUser().getPlaceBirth());
			teacherDTO.setResidence(teacher.getUser().getResidence());
			return teacherDTO;
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
	}
	/*
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public void removeUserById(int id) throws UserNotFoundException {
		
		
		try {
			User user = userRepository.getOne(id);
			userRepository.delete(user);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
		
	}*/
	
	/*@Transactional(rollbackFor=UserNotFoundException.class)
	public User checkUser(String email) throws UserNotFoundException {
		
		try {
			User user = userRepository.findByEmail(email);
			return user;
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
		
	}*/
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public UserDTO login(String email, String token) throws UserNotFoundException {
		
		User user = userRepository.checkUser(email);
		
		try {
			
				UserDTO userDTO = new UserDTO();
				userDTO.setIduser(user.getIduser());
				userDTO.setName(user.getName());
				userDTO.setSurname(user.getSurname());
				userDTO.setEmail(user.getEmail());
				userDTO.setDateBirth(user.getDateBirth());
				userDTO.setPlaceBirth(user.getPlaceBirth());
				userDTO.setResidence(user.getResidence());
				userDTO.setDomicile(user.getDomicile());
				userDTO.setPhone(user.getPhone());
				userDTO.setSex(user.getSex());
				userDTO.setCitizenship(user.getCitizenship());
				userDTO.setToken(token);
				if(userRepository.isStudent(user.getIduser())) {
					userDTO.setType("student");
				} else if (userRepository.isTeacher(user.getIduser())) {
					userDTO.setType("teacher");
				} else {
					userDTO.setType("employee");
				}
				return userDTO;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
		
	}
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public User getUser(int idUser) throws UserNotFoundException {
		try {
			return userRepository.findById(idUser).get();
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
	}
	
	/*@Transactional
	public List<User> getColleagues(User user, String keyword) throws UserNotFoundException {
		try{
			User existingUser = userRepository.findById(user.getIduser()).get();
			return userRepository.getColleagues(existingUser.getCourse().getIdcourse(), existingUser.getIduser(), keyword);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
	}*/
	
	/*@Transactional
	public boolean login(String email, String password){
		return userRepository.login(email, password);
	}*/
}
