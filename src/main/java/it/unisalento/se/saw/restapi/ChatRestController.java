package it.unisalento.se.saw.restapi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.IMessageService;
import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.Iservices.IChatService;
import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.Iservices.IMessageService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Message;
import it.unisalento.se.saw.domain.Message;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ChatDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.MessageDTO;
import it.unisalento.se.saw.dto.PreviewChatDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.UserUnauthorizedException;
import it.unisalento.se.saw.exceptions.UserUnsubscribedException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/chat")
public class ChatRestController {
	
	@Autowired
	IChatService chatService;
	
	public ChatRestController() {
		super();
	}
	
	public ChatRestController(IChatService chatService) {
		this.chatService = chatService;
	}
	
	@GetMapping(value="/getPreviews/{ssn}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
	public List<PreviewChatDTO> getPreviews(@PathVariable("ssn")String ssn){
		return chatService.getAllPreviews(ssn);
	}
	
	@PostMapping(value="/getPreviews/search/{ssn}&{keyword}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<PreviewChatDTO> getColleagues(@RequestBody String ssn, @PathVariable String keyword) throws UserNotFoundException {
		return chatService.search(ssn, keyword);
	}
	
	@PostMapping(value="/{ssn}", produces=MediaType.APPLICATION_JSON_VALUE) //click su una chat
	public ChatDTO getChat(@RequestBody PreviewChatDTO previewChatDTO, @PathVariable String ssn) throws UserUnsubscribedException, ChatNotFoundException {
		if(previewChatDTO.isPublic()) { //chat pubblica
			return chatService.getSubjectChat(previewChatDTO.getId());
		} else { //chat privata
			return chatService.getPrivateChat(ssn, previewChatDTO.getId());
		}
	}
	
	/*@PostMapping(value="/insert", produces=MediaType.APPLICATION_JSON_VALUE) //inserisci messaggio
	public MessageDTO insertMessage(@RequestBody MessageDTO messageDTO) {
		Message messageOn = messageService.save(this.MessageDTOtoEntity(messageDTO));

		return this.MessageEntityToDTO(messageOn);
	}*/

}
