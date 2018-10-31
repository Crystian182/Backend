package it.unisalento.se.saw.restapi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.Type;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.LoginDTO;
import it.unisalento.se.saw.dto.TypeDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongPasswordException;
import it.unisalento.se.saw.models.Login;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;

@RestController
public class LoginRestController {
	
	@Autowired
	IUserService userService;
	
	public LoginRestController() {
		super();
	}
	
	public LoginRestController(IUserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDTO checkUser(@RequestBody LoginDTO loginDTO) throws UserNotFoundException, WrongPasswordException {
	
		User user = userService.checkUser(this.DTOtoEntity(loginDTO));
		return this.EntityToDTO(user);
	}
	
	public Login DTOtoEntity(LoginDTO loginDTO) {
		
		Login login = new Login();
		login.setEmail(loginDTO.getEmail());
		login.setPassword(loginDTO.getPassword());

		return login;
	}
	
	public UserDTO EntityToDTO(User user) {
		
		TypeDTO typeDTO = new TypeDTO();
		typeDTO.setIdType(user.getType().getIdtype());
		typeDTO.setDescription(user.getType().getDescription());
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getIduser());
		userDTO.setName(user.getName());
		userDTO.setSurname(user.getSurname());
		userDTO.setUsername(user.getUsername());
		userDTO.setTypeDTO(typeDTO);
		return userDTO;
	}
}
