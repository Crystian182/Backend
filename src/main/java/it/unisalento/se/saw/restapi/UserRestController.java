package it.unisalento.se.saw.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.LoginDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongCredentialsException;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	IUserService userService;
	
	public UserRestController() {
		super();
	}
	
	public UserRestController(IUserService userService) {
		this.userService = userService;
	}
	
	/*@PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDTO login(@RequestBody LoginDTO request) throws UserNotFoundException, WrongCredentialsException {
		return userService.login(request);
	}*/
	
	/*@RequestMapping(value="/getAll", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAll(){
		return userService.getAll();
		
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	
	public UserDTO getById(@PathVariable("id")int id) throws UserNotFoundException{
		User user = userService.getById(id);
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Cristian");
		userDTO.setSurname("Vergallo");
		return userDTO;
		
	}
	
	
	@PostMapping(value="/save", consumes=MediaType.APPLICATION_JSON_VALUE)
	public User post(@RequestBody UserDTO userDTO) {
		User user = new User();
		/*user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());*/
		/*return userService.save(user);
	}*/
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDTO getById(@PathVariable("id")int id) throws UserNotFoundException{
		UserDTO userDTO = userService.getById(id);
		return userDTO;
	}
	
	@GetMapping(value="/getTeachers", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TeacherDTO> getTeachers() throws UserNotFoundException{
		List<TeacherDTO> teacherDTOs = userService.getTeachers();
		return teacherDTOs;
	}
	
	@GetMapping(value="/getTeacherById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public TeacherDTO getTeachersById(@PathVariable("id")int id) throws UserNotFoundException{
		TeacherDTO teacherDTO = userService.getTeacherById(id);
		return teacherDTO;
	}
}
