package it.unisalento.se.saw.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.LoginDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongPasswordException;
import it.unisalento.se.saw.models.Login;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class UserService implements IUserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional(readOnly=true)
	public List<User> getAll(){
		return userRepository.findAll();	
	}
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}
	
	/*@Transactional
	public User getById(int id) throws UserNotFoundException {
		try {
			User user = userRepository.getOne(id);
			return user;
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
	}
	
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
	public UserDTO login(LoginDTO request) throws UserNotFoundException, WrongPasswordException {
		
		User user = userRepository.checkUser(request.getEmail());
		
		try {
			if(user.getPassword().equals(request.getPassword())) {
				UserDTO userDTO = new UserDTO();
				userDTO.setIduser(user.getIduser());
				userDTO.setSsn(user.getSsn());
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
				if(userRepository.isStudent(user.getIduser())) {
					userDTO.setType("student");
				} else if (userRepository.isTeacher(user.getIduser())) {
					userDTO.setType("teacher");
				} else {
					userDTO.setType("employee");
				}
				return userDTO;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
		
		throw new WrongPasswordException();
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
