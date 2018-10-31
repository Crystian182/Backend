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

import it.unisalento.se.saw.Iservices.IPrivateChatService;
import it.unisalento.se.saw.Iservices.IPublicChatService;
import it.unisalento.se.saw.Iservices.IMessageService;
import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.Iservices.IMessageService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.domain.Message;
import it.unisalento.se.saw.domain.Privatechat;
import it.unisalento.se.saw.domain.Publicchat;
import it.unisalento.se.saw.domain.Message;
import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ChatDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseDTO;
import it.unisalento.se.saw.dto.MessageDTO;
import it.unisalento.se.saw.dto.SubjectOfStudyDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TypeDTO;
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
	IPrivateChatService privateChatService;
	
	@Autowired
	IMessageService messageService;
	
	@Autowired
	IPublicChatService publicChatService;
	
	@Autowired
	IUserService userService;
	
	public ChatRestController() {
		super();
	}
	
	public ChatRestController(IPrivateChatService privateChatService, IPublicChatService publicChatService, IMessageService messageService, IUserService userService) {
		this.privateChatService = privateChatService;
		this.publicChatService = publicChatService;
		this.messageService = messageService;
		this.userService = userService;
	}
	
	@GetMapping(value="/getAll/{userId}", produces=MediaType.APPLICATION_JSON_VALUE) //home della chat
	public List<ChatDTO> getAll(@PathVariable("userId")int id){
		List<ChatDTO> chatDTOs = new ArrayList<ChatDTO>();
		
		List<Privatechat> privateChats = privateChatService.getAll(id);
		for(int i=0; i<privateChats.size(); i++) {
			Privatechat chat = privateChats.get(i);
			ChatDTO chatDTO = PrivateChatEntityToDTO(chat, id);
			
			chatDTOs.add(chatDTO);
		}
		
		List<Publicchat> publicChats = publicChatService.getAll(id);
		for(int j=0; j<publicChats.size(); j++) {
			Publicchat chat = publicChats.get(j);
			ChatDTO chatDTO = PublicChatEntityToDTO(chat, id);
			
			chatDTOs.add(chatDTO);
		}
		
		Collections.sort(chatDTOs, new Comparator<ChatDTO>() {
		    public int compare(ChatDTO c1, ChatDTO c2) {
		    	return c1.getLastMessage().getDate().compareTo(c2.getLastMessage().getDate());
		    }
		});

		return chatDTOs;
	}
	
	@PostMapping(value="/search/{keyword}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ChatDTO> getColleagues(@RequestBody UserDTO userDTO, @PathVariable String keyword) throws UserNotFoundException {
		List<ChatDTO> chatDTOs = new ArrayList<ChatDTO>();

		List<User> colleagues = userService.getColleagues(this.UserDTOtoEntity(userDTO), keyword);
		for (int i=0; i<colleagues.size(); i++) {
				UserDTO colleagueDTO = new UserDTO();
				colleagueDTO.setId(colleagues.get(i).getIduser());
				colleagueDTO.setName(colleagues.get(i).getName());
				colleagueDTO.setSurname(colleagues.get(i).getSurname());
				colleagueDTO.setUsername(colleagues.get(i).getUsername());
				
				ChatDTO chatDTO = new ChatDTO();
				chatDTO.setUser(colleagueDTO);
				chatDTO.setTitle(colleagues.get(i).getName() + " " + colleagues.get(i).getSurname());
				
				chatDTOs.add(chatDTO);
		}
		
		List<Publicchat> publicChats = publicChatService.search(this.UserDTOtoEntity(userDTO), keyword);
		for(int j=0; j<publicChats.size(); j++) {
			ChatDTO chatDTO = PublicChatEntityToDTO(publicChats.get(j), userDTO.getId());
			chatDTOs.add(chatDTO);
		}
		
		Collections.sort(chatDTOs, new Comparator<ChatDTO>() {
		    public int compare(ChatDTO c1, ChatDTO c2) {
		    	return c1.getTitle().compareTo(c2.getTitle());
		    }
		});
		
		return chatDTOs;
	}
	
	@PostMapping(value="/{userid}", produces=MediaType.APPLICATION_JSON_VALUE) //click su una chat
	public ChatDTO getChat(@RequestBody ChatDTO chatDTO, @PathVariable int userid) throws UserUnsubscribedException, ChatNotFoundException {
		ChatDTO chat = new ChatDTO();
		if(chatDTO.isPublic()) { //chat pubblica
			if(publicChatService.checkSubscribe(userid, this.PublicChatDTOtoEntity(chatDTO))) {
				chat = this.BuildPublicChatDTO(publicChatService.getChat(this.PublicChatDTOtoEntity(chatDTO)), userid);
			} else {
				throw new UserUnsubscribedException();
			}
		} else { //chat privata
			try {
				chat = this.BuildPrivateChatDTO(privateChatService.getChat(userid, chatDTO.getUser().getId()), userid);
			} catch (Exception e) {
				throw new UserUnsubscribedException();
			}
		}
		return chat;
	}
	
	@PostMapping(value="/subscribe/{userid}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ChatDTO subscribe(@RequestBody ChatDTO chatDTO, @PathVariable int userid) throws ChatNotFoundException {
		publicChatService.subscribe(userid, chatDTO.getIdChat());
		Publicchat chat = publicChatService.getChat(this.PublicChatDTOtoEntity(chatDTO));
		return this.BuildPublicChatDTO(chat, userid);
	}
	
	@PostMapping(value="/insert", produces=MediaType.APPLICATION_JSON_VALUE) //inserisci messaggio
	public MessageDTO insertMessage(@RequestBody MessageDTO messageDTO) {
		Message messageOn = messageService.save(this.MessageDTOtoEntity(messageDTO));

		return this.MessageEntityToDTO(messageOn);
	}
	
	public ChatDTO PrivateChatEntityToDTO (Privatechat chat, int userid) {
		List<Message> allMessages = messageService.getAllPrivates(chat.getIdprivatechat());

		ChatDTO chatDTO = new ChatDTO();
		chatDTO.setIdChat(chat.getIdprivatechat());
		
		UserDTO senderDTO = new UserDTO();
		if(chat.getUserByUserIduser().getIduser() != userid) {
			senderDTO.setId(chat.getUserByUserIduser().getIduser());
			senderDTO.setName(chat.getUserByUserIduser().getName());
			senderDTO.setSurname(chat.getUserByUserIduser().getSurname());
			senderDTO.setUsername(chat.getUserByUserIduser().getUsername());
			chatDTO.setTitle(chat.getUserByUserIduser().getName() + " " + chat.getUserByUserIduser().getSurname());
		} else {
			senderDTO.setId(chat.getUserByUserIduser1().getIduser());
			senderDTO.setName(chat.getUserByUserIduser1().getName());
			senderDTO.setSurname(chat.getUserByUserIduser1().getSurname());
			senderDTO.setUsername(chat.getUserByUserIduser1().getUsername());
			chatDTO.setTitle(chat.getUserByUserIduser1().getName() + " " + chat.getUserByUserIduser1().getSurname());
		}
		chatDTO.setUser(senderDTO);
		
		if(!allMessages.isEmpty()) {
			MessageDTO lastMessageDTO = new MessageDTO();
			lastMessageDTO.setId(allMessages.get(0).getIdmessage());
			
			UserDTO lastSenderDTO = new UserDTO();
			lastSenderDTO.setId(allMessages.get(0).getUser().getIduser());
			lastSenderDTO.setName(allMessages.get(0).getUser().getName());
			lastSenderDTO.setSurname(allMessages.get(0).getUser().getSurname());
			lastSenderDTO.setUsername(allMessages.get(0).getUser().getUsername());
			
			lastMessageDTO.setSender(lastSenderDTO);
			lastMessageDTO.setText(allMessages.get(0).getText());
			lastMessageDTO.setDate(allMessages.get(0).getDate());
			
			chatDTO.setLastMessage(lastMessageDTO);
		}
		
		return chatDTO;
	}
	
	public Privatechat PrivateChatDTOtoEntity(ChatDTO chatDTO) {
		Privatechat chat = new Privatechat();
		chat.setIdprivatechat(chatDTO.getIdChat());
		return chat;
	}
	
	public ChatDTO PublicChatEntityToDTO (Publicchat chat, int userid) {
		List<Message> allMessages = messageService.getAllPublics(chat.getIdpublicchat());
		
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setIdcourse(chat.getSubjectofstudy().getCourse().getIdcourse());
		courseDTO.setName(chat.getSubjectofstudy().getCourse().getName());
		courseDTO.setDescription(chat.getSubjectofstudy().getCourse().getDescription());
		courseDTO.setYears(chat.getSubjectofstudy().getCourse().getYears());
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setId(chat.getSubjectofstudy().getUser().getIduser());
		teacherDTO.setName(chat.getSubjectofstudy().getUser().getName());
		teacherDTO.setSurname(chat.getSubjectofstudy().getUser().getSurname());

		SubjectOfStudyDTO subjectOfStudyDTO = new SubjectOfStudyDTO();
		subjectOfStudyDTO.setId(chat.getSubjectofstudy().getIdsubjectofstudy());
		subjectOfStudyDTO.setName(chat.getSubjectofstudy().getName());
		subjectOfStudyDTO.setDescription(chat.getSubjectofstudy().getDescription());
		subjectOfStudyDTO.setCourseDTO(courseDTO);
		subjectOfStudyDTO.setTeacherDTO(teacherDTO);

		ChatDTO chatDTO = new ChatDTO();
		chatDTO.setIdChat(chat.getIdpublicchat());
		chatDTO.setTitle(chat.getSubjectofstudy().getName());
		chatDTO.setSubjectOfStudy(subjectOfStudyDTO);
		
		if(!allMessages.isEmpty()) {
			UserDTO senderDTO = new UserDTO();
			senderDTO.setId(allMessages.get(0).getUser().getIduser());
			senderDTO.setName(allMessages.get(0).getUser().getName());
			senderDTO.setSurname(allMessages.get(0).getUser().getSurname());
			senderDTO.setUsername(allMessages.get(0).getUser().getUsername());
			
			MessageDTO lastMessageDTO = new MessageDTO();
			lastMessageDTO.setId(allMessages.get(0).getIdmessage());
			lastMessageDTO.setText(allMessages.get(0).getText());
			lastMessageDTO.setSender(senderDTO);
			lastMessageDTO.setDate(allMessages.get(0).getDate());
			
			chatDTO.setLastMessage(lastMessageDTO);
		}
		
		chatDTO.setPublic(true);
		chatDTO.setSubscribed(publicChatService.checkSubscribe(userid, chat));
		
		return chatDTO;
		
	}
	
	public Publicchat PublicChatDTOtoEntity(ChatDTO chatDTO) {
		Publicchat chat = new Publicchat();
		chat.setIdpublicchat(chatDTO.getIdChat());
		return chat;
	}
	
	public User UserDTOtoEntity(UserDTO userDTO) {
		
		Course course = new Course();
		course.setIdcourse(userDTO.getCourseDTO().getIdcourse());
		course.setName(userDTO.getCourseDTO().getName());
		course.setYears(userDTO.getCourseDTO().getYears());
		
		User user = new User();
		user.setIduser(userDTO.getId());
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());
		user.setCourse(course);
		
		return user;
	}
	
	public ChatDTO BuildPublicChatDTO (Publicchat chat, int userid) {
		List<Message> allMessages = messageService.getAllPublics(chat.getIdpublicchat());
		
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setIdcourse(chat.getSubjectofstudy().getCourse().getIdcourse());
		courseDTO.setName(chat.getSubjectofstudy().getCourse().getName());
		courseDTO.setDescription(chat.getSubjectofstudy().getCourse().getDescription());
		courseDTO.setYears(chat.getSubjectofstudy().getCourse().getYears());
		
		List<MessageDTO> allMessagesDTO = new ArrayList<MessageDTO>();
		for(int i=0; i<allMessages.size(); i++) {
			UserDTO senderDTO = new UserDTO();
			senderDTO.setId(allMessages.get(i).getUser().getIduser());
			senderDTO.setName(allMessages.get(i).getUser().getName());
			senderDTO.setSurname(allMessages.get(i).getUser().getSurname());
			senderDTO.setUsername(allMessages.get(i).getUser().getUsername());
			
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setId(allMessages.get(i).getIdmessage());
			messageDTO.setText(allMessages.get(i).getText());
			messageDTO.setSender(senderDTO);
			messageDTO.setDate(allMessages.get(i).getDate());
			
			allMessagesDTO.add(messageDTO);
		}
		
		Collections.sort(allMessagesDTO, new Comparator<MessageDTO>() {
		    public int compare(MessageDTO m1, MessageDTO m2) {
		    	return m1.getDate().compareTo(m2.getDate());
		    }
		});
		
		SubjectOfStudyDTO subjectOfStudyDTO = new SubjectOfStudyDTO();
		subjectOfStudyDTO.setId(chat.getSubjectofstudy().getIdsubjectofstudy());
		subjectOfStudyDTO.setName(chat.getSubjectofstudy().getName());
		subjectOfStudyDTO.setDescription(chat.getSubjectofstudy().getDescription());
		subjectOfStudyDTO.setCourseDTO(courseDTO);

		ChatDTO chatDTO = new ChatDTO();
		chatDTO.setIdChat(chat.getIdpublicchat());
		chatDTO.setTitle(chat.getSubjectofstudy().getName());
		chatDTO.setAllMessages(allMessagesDTO);
		chatDTO.setSubjectOfStudy(subjectOfStudyDTO);
		chatDTO.setPublic(true);
		chatDTO.setSubscribed(publicChatService.checkSubscribe(userid, chat));
		
		return chatDTO;
		
	}
	
	public ChatDTO BuildPrivateChatDTO (Privatechat chat, int userid) {
		List<Message> allMessages = messageService.getAllPrivates(chat.getIdprivatechat());
		
		List<MessageDTO> allMessagesDTO = new ArrayList<MessageDTO>();
		for(int i=0; i<allMessages.size(); i++) {
			UserDTO senderDTO = new UserDTO();
			senderDTO.setId(allMessages.get(i).getUser().getIduser());
			senderDTO.setName(allMessages.get(i).getUser().getName());
			senderDTO.setSurname(allMessages.get(i).getUser().getSurname());
			senderDTO.setUsername(allMessages.get(i).getUser().getUsername());
			
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setId(allMessages.get(i).getIdmessage());
			messageDTO.setText(allMessages.get(i).getText());
			messageDTO.setSender(senderDTO);
			messageDTO.setDate(allMessages.get(i).getDate());
			
			allMessagesDTO.add(messageDTO);
		}
		
		Collections.sort(allMessagesDTO, new Comparator<MessageDTO>() {
		    public int compare(MessageDTO m1, MessageDTO m2) {
		    	return m1.getDate().compareTo(m2.getDate());
		    }
		});
		
		ChatDTO chatDTO = new ChatDTO();
		chatDTO.setIdChat(chat.getIdprivatechat());
		chatDTO.setAllMessages(allMessagesDTO);

		UserDTO userDTO = new UserDTO();
		if(chat.getUserByUserIduser().getIduser() != userid) {
			userDTO.setId(chat.getUserByUserIduser().getIduser());
			userDTO.setName(chat.getUserByUserIduser().getName());
			userDTO.setSurname(chat.getUserByUserIduser().getSurname());
			userDTO.setUsername(chat.getUserByUserIduser().getUsername());
			chatDTO.setTitle(chat.getUserByUserIduser().getName() + " " + chat.getUserByUserIduser().getSurname());
		} else {
			userDTO.setId(chat.getUserByUserIduser1().getIduser());
			userDTO.setName(chat.getUserByUserIduser1().getName());
			userDTO.setSurname(chat.getUserByUserIduser1().getSurname());
			userDTO.setUsername(chat.getUserByUserIduser1().getUsername());
			chatDTO.setTitle(chat.getUserByUserIduser1().getName() + " " + chat.getUserByUserIduser1().getSurname());
		}
		chatDTO.setUser(userDTO);
		
		
		return chatDTO;
		
	}
	
	public Message MessageDTOtoEntity (MessageDTO messageDTO) {
		Message message = new Message();
		if(messageService.checkPublicOrNot(messageDTO.getChat().getIdChat())) { //chat pubblica
			Publicchat chat = new Publicchat();
			chat.setIdpublicchat(messageDTO.getChat().getIdChat());
			
			message.setPublicchat(chat);
		} else { //chat privata
			Privatechat chat = new Privatechat();
			chat.setIdprivatechat(messageDTO.getChat().getIdChat());

			message.setPrivatechat(chat);		
		}
		
		User sender = new User();
		sender.setIduser(messageDTO.getSender().getId());

		message.setText(messageDTO.getText());
		message.setDate(new Timestamp(System.currentTimeMillis()));
		message.setUser(sender);

		return message;
	}
	
	public MessageDTO MessageEntityToDTO (Message message) {
		
		ChatDTO chatDTO = new ChatDTO();
		if(message.getPublicchat().getIdpublicchat() != null) { //chat pubblica
			chatDTO.setIdChat(message.getPublicchat().getIdpublicchat());
		} else { //chatprivata
			chatDTO.setIdChat(message.getPrivatechat().getIdprivatechat());
		}

		UserDTO senderDTO = new UserDTO();
		senderDTO.setId(message.getUser().getIduser());
		senderDTO.setName(message.getUser().getName());
		senderDTO.setSurname(message.getUser().getSurname());
		senderDTO.setUsername(message.getUser().getUsername());
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setId(message.getIdmessage());
		messageDTO.setText(message.getText());
		messageDTO.setDate(message.getDate());
		messageDTO.setChat(chatDTO);
		messageDTO.setSender(senderDTO);

		return messageDTO;
	}

}
