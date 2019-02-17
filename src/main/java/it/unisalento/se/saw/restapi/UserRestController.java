package it.unisalento.se.saw.restapi;

import java.io.IOException;
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
import it.unisalento.se.saw.domain.StudentHasDegreeCourse;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.StudentHasDegreeCourseDTO;
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
	
	@PostMapping(value="/save", consumes=MediaType.APPLICATION_JSON_VALUE)
	public UserDTO save(@RequestBody UserDTO userDTO) throws IOException {
		return userService.save(userDTO);
	}
	
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
	
	@GetMapping(value="/getInfoStudent/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public StudentHasDegreeCourseDTO getInfo(@PathVariable("id")int id) throws UserNotFoundException{
		return userService.getInfo(id);
	}
}
