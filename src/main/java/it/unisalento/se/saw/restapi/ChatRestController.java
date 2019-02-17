package it.unisalento.se.saw.restapi;

import java.io.IOException;
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
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
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
	
	/*@GetMapping(value="/getPreviews/{userid}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
	public List<PreviewChatDTO> getPreviews(@PathVariable("userid")int userid) throws IOException{
		return chatService.getAllPreviews(userid);
	}
	
	@GetMapping(value="/getMessages/idchat={chatid}&iduser={iduser}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
	public List<MessageDTO> getMessages(@PathVariable("chatid")int chatid, @PathVariable("iduser")int iduser) throws IOException{
		return chatService.getMessages(chatid, iduser);
	}
	
	@GetMapping(value="/readall/idchat={chatid}&iduser={iduser}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
	public void readAll(@PathVariable("chatid")int chatid, @PathVariable("iduser")int iduser) {
		chatService.readAll(chatid, iduser);
	}
	
	@GetMapping(value="/isread/idmessage={messageid}&iduser={iduser}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
	public boolean isRead(@PathVariable("messageidd")int messageid, @PathVariable("iduser")int iduser) {
		return chatService.isRead(messageid, iduser);
	}
	
	/*@PostMapping(value="/send/{chatid}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
	public void sendMessage(@PathVariable("chatid")int chatid, @RequestBody MessageDTO messageDTO){
		chatService.sendMessage(chatid, messageDTO);
	}*/
	
	@GetMapping(value="/getContacts/userid={userid}&keyword={keyword}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<PreviewChatDTO> getContacts(@PathVariable("userid")int userid, @PathVariable("keyword")String keyword) throws IOException{
		return chatService.getContacts(userid, keyword);
	}
	
	@GetMapping(value="/getProfileUser/{userid}", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDTO getProfileUser(@PathVariable("userid")int userid) throws IOException{
		return chatService.getProfileUser(userid);
	}
	
	@GetMapping(value="/getSubscribedCourses/userid={userid}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<PreviewChatDTO> getSubscribedCourses(@PathVariable("userid")int userid){
		return chatService.getSubscribedCourses(userid);
	}
	
	@GetMapping(value="/getpropic/{userid}", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDTO getPropic(@PathVariable("userid")int userid) throws IOException{
		UserDTO userDTO = new UserDTO();
		userDTO.setPropic(chatService.getPropic(userid));
		return userDTO;
	}
	
	/*@GetMapping(value="/getPreview/user1={userid1}&user2={userid2}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
	public PreviewChatDTO getPreview(@PathVariable("userid1")int userid1, @PathVariable("userid2")int userid2) throws IOException{
		return chatService.getPreview(userid1, userid2);
	}
	
	/*@GetMapping(value="/getPreviews/{ssn}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
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
