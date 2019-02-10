package it.unisalento.se.saw.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IChatService;
import it.unisalento.se.saw.domain.DegreeCourse;
//import it.unisalento.se.saw.domain.PrivateChat;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.MessageDTO;
import it.unisalento.se.saw.dto.PreviewChatDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;
import it.unisalento.se.saw.repositories.DegreeCourseRepository;
//import it.unisalento.se.saw.repositories.EnrollmentRepository;
//import it.unisalento.se.saw.repositories.PrivateChatRepository;
/*import it.unisalento.se.saw.repositories.PrivateMessageRepository;
import it.unisalento.se.saw.repositories.SubjectChatHasStudentRepository;
import it.unisalento.se.saw.repositories.SubjectChatRepository;*/
import it.unisalento.se.saw.repositories.SubjectRepository;
import it.unisalento.se.saw.repositories.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;

@Service
public class ChatService implements IChatService {
	
	//@Autowired
	//PrivateMessageRepository privateMessageRepository;
	
	/*@Autowired
	SubjectMessageRepository subjectMessageRepository;
	
	/*@Autowired
	PrivateChatRepository privateChatRepository;
	
	//@Autowired
	//SubjectChatRepository subjectChatRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	DegreeCourseRepository courseRepository;
	
	/*@Autowired
	SubjectChatHasStudentRepository enrollmentSubjectChatRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;*/
	
	@Autowired
	DegreeCourseRepository courseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	private static final String location = "C:\\Users\\Federico\\Desktop\\Computer Engineering\\Backend\\Backend\\src\\main\\resources\\users\\";
	
	/*@Transactional(readOnly=true)
	public List<PreviewChatDTO> getAllPreviews(int iduser) throws IOException{
		List<PreviewChatDTO> previewChatDTOs = new ArrayList<PreviewChatDTO>();
		List<Chat> chatPubbliche = new ArrayList<Chat>();
		if(userRepository.isTeacher(iduser)) {
			chatPubbliche = chatRepository.getTeacherPublicChat(iduser);
		} else {
			chatPubbliche = chatRepository.getStudentPublicChat(iduser);
		}
		for(int i=0; i<chatPubbliche.size(); i++) {
			PreviewChatDTO previewChat = new PreviewChatDTO();
			previewChat.setId(chatPubbliche.get(i).getIdchat());
			previewChat.setTitle(chatPubbliche.get(i).getPublicChat().getSubject().getTypeSubject().getName());
			
			List<Message> messages = messageRepository.getMessagesFromChatId(chatPubbliche.get(i).getIdchat());
			if(messages.size() > 0) {
				MessageDTO lastMessageDTO = new MessageDTO();
				lastMessageDTO.setId(messages.get(messages.size()-1).getIdmessage());
				lastMessageDTO.setText(messages.get(messages.size()-1).getText());
				lastMessageDTO.setDate(messages.get(messages.size()-1).getDate());
				
				UserDTO sender = new UserDTO();
				sender.setIduser(messages.get(messages.size()-1).getUser().getIduser());
				sender.setName(messages.get(messages.size()-1).getUser().getName());
				sender.setSurname(messages.get(messages.size()-1).getUser().getSurname());
				
				if(messages.get(messages.size()-1).getUser().getIduser() != iduser) {
					lastMessageDTO.setRead(userReadsMessageRepository.isRead(iduser, messages.get(messages.size()-1).getIdmessage()));
				} else {
					lastMessageDTO.setRead(false);
				}
				
				lastMessageDTO.setSender(sender);
				previewChat.setLastMessage(lastMessageDTO);
			}
			
			previewChatDTOs.add(previewChat);
			
		}
		
		List<Chat> chatPrivate = chatRepository.getPrivateChat(iduser);
		for(int i=0; i<chatPrivate.size(); i++) {
			PreviewChatDTO previewChat = new PreviewChatDTO();
			previewChat.setId(chatPrivate.get(i).getIdchat());
			if(chatPrivate.get(i).getPrivateChat().getUserByUserIduser().getIduser() == iduser) {
				previewChat.setTitle(chatPrivate.get(i).getPrivateChat().getUserByUserIduser1().getName() + " " + chatPrivate.get(i).getPrivateChat().getUserByUserIduser1().getSurname());
				
				UserDTO toUser = new UserDTO();
				toUser.setIduser(chatPrivate.get(i).getPrivateChat().getUserByUserIduser1().getIduser());
				toUser.setName(chatPrivate.get(i).getPrivateChat().getUserByUserIduser1().getName());
				toUser.setSurname(chatPrivate.get(i).getPrivateChat().getUserByUserIduser1().getSurname());
				
				try {
					toUser.setPropic(this.getUserImage(chatPrivate.get(i).getPrivateChat().getUserByUserIduser1().getFile().getIdfile()));
				} catch (NullPointerException e) {
				}
				previewChat.setToUser(toUser);
			} else {
				previewChat.setTitle(chatPrivate.get(i).getPrivateChat().getUserByUserIduser().getName() + " " + chatPrivate.get(i).getPrivateChat().getUserByUserIduser().getSurname());
				UserDTO toUser = new UserDTO();
				toUser.setIduser(chatPrivate.get(i).getPrivateChat().getUserByUserIduser().getIduser());
				toUser.setName(chatPrivate.get(i).getPrivateChat().getUserByUserIduser().getName());
				toUser.setSurname(chatPrivate.get(i).getPrivateChat().getUserByUserIduser().getSurname());
				
				try {
					toUser.setPropic(this.getUserImage(chatPrivate.get(i).getPrivateChat().getUserByUserIduser().getFile().getIdfile()));
				} catch (NullPointerException e) {
				}
				previewChat.setToUser(toUser);
			}
			
			List<Message> messages = messageRepository.getMessagesFromChatId(chatPrivate.get(i).getIdchat());
			if(messages.size() > 0) {
				MessageDTO lastMessageDTO = new MessageDTO();
				lastMessageDTO.setId(messages.get(messages.size()-1).getIdmessage());
				lastMessageDTO.setText(messages.get(messages.size()-1).getText());
				lastMessageDTO.setDate(messages.get(messages.size()-1).getDate());
				
				UserDTO sender = new UserDTO();
				sender.setIduser(messages.get(messages.size()-1).getUser().getIduser());
				sender.setName(messages.get(messages.size()-1).getUser().getName());
				sender.setSurname(messages.get(messages.size()-1).getUser().getSurname());
				
				if(messages.get(messages.size()-1).getUser().getIduser() != iduser) {
					lastMessageDTO.setRead(userReadsMessageRepository.isRead(iduser, messages.get(messages.size()-1).getIdmessage()));
				} else {
					lastMessageDTO.setRead(false);
				}
				
				lastMessageDTO.setSender(sender);
				previewChat.setLastMessage(lastMessageDTO);
			}
			
			previewChatDTOs.add(previewChat);
			
		}
		Collections.sort(previewChatDTOs, new Comparator<PreviewChatDTO>() {
            @Override
            public int compare(PreviewChatDTO o1, PreviewChatDTO o2) {
                return o2.getLastMessage().getDate().compareTo(o1.getLastMessage().getDate());
            }
        });
		return previewChatDTOs;

		}
	
	@Transactional(readOnly=true)
	public PreviewChatDTO getPreview(int iduser1, int iduser2) throws IOException{
		
		Chat chatPrivate = chatRepository.getExistingPrivateChat(iduser1, iduser2);
		
			PreviewChatDTO previewChat = new PreviewChatDTO();
			previewChat.setId(chatPrivate.getIdchat());
			
		return previewChat;

		}
	
	
	@Transactional(readOnly=true)
	public List<MessageDTO> getMessages(int idchat, int iduser) throws IOException{
		List<Message> messages = messageRepository.getMessagesFromChatId(idchat);
		List<MessageDTO> messageDTOs = new ArrayList<MessageDTO>();
		for(int i=0; i<messages.size(); i++) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setId(messages.get(i).getIdmessage());
			messageDTO.setText(messages.get(i).getText());
			messageDTO.setDate(messages.get(i).getDate());
			
			UserDTO sender = new UserDTO();
			sender.setIduser(messages.get(i).getUser().getIduser());
			sender.setName(messages.get(i).getUser().getName());
			sender.setSurname(messages.get(i).getUser().getSurname());
			try {
				sender.setPropic(this.getUserImage(messages.get(i).getUser().getFile().getIdfile()));
			} catch (NullPointerException e) {
			}
			
			if(messages.get(i).getUser().getIduser() != iduser) {
				messageDTO.setRead(userReadsMessageRepository.isRead(iduser, messages.get(i).getIdmessage()));
			} else {
				messageDTO.setRead(false);
			}
			
			messageDTO.setSender(sender);
			messageDTOs.add(messageDTO);
		}
		
		return messageDTOs;

	}
	
	@Transactional(readOnly=true)
	public int sendMessage(MessageDTO messageDTO){
		if(messageDTO.getChat().getId() == 0) {
			Chat chat = new Chat();
			chat.setDate(new Date());
			
			Chat newChat = chatRepository.save(chat);
			
			User user1 = new User();
			user1.setIduser(messageDTO.getSender().getIduser());
			
			User user2 = new User();
			user2.setIduser(messageDTO.getChat().getToUser().getIduser());
			
			PrivateChat privateChat = new PrivateChat();
			privateChat.setUserByUserIduser(user1);
			privateChat.setUserByUserIduser1(user2);
			privateChat.setChat(newChat);
			
			privateChatRepository.save(privateChat);
			
			User user = new User();
			user.setIduser(messageDTO.getSender().getIduser());
			
			Message message = new Message();
			message.setUser(user);
			message.setText(messageDTO.getText());
			message.setDate(messageDTO.getDate());
			message.setChat(newChat);
			
			messageRepository.save(message);
			return newChat.getIdchat();
		} else {
			User user = new User();
			user.setIduser(messageDTO.getSender().getIduser());
			
			Chat chat = new Chat();
			chat.setIdchat(messageDTO.getChat().getId());
			
			Message message = new Message();
			message.setUser(user);
			message.setText(messageDTO.getText());
			message.setDate(messageDTO.getDate());
			message.setChat(chat);
			
			messageRepository.save(message);
			
			return messageDTO.getChat().getId();
		}
		

	}
	*/
	@Transactional(readOnly=true)
	public List<PreviewChatDTO> getContacts(int iduser, String keyword) throws IOException{
		List<PreviewChatDTO> previewChatDTOs = new ArrayList<PreviewChatDTO>();
		List<Subject> materie = new ArrayList<Subject>();
		if(userRepository.isTeacher(iduser)) {
			materie = subjectRepository.searchByIdTeacher(iduser, keyword);
		} else {
			materie = subjectRepository.searchByIdStudent(iduser, keyword);
		}
		for(int i=0; i<materie.size(); i++) {
			PreviewChatDTO previewChat = new PreviewChatDTO();
			
			SubjectDTO subjectDTO = new SubjectDTO();
			
			TypeSubjectDTO type = new TypeSubjectDTO();
			type.setIdtypeSubject(materie.get(i).getTypeSubject().getIdtypeSubject());
			type.setName(materie.get(i).getTypeSubject().getName());
			
			AcademicYearDTO aa = new AcademicYearDTO();
			aa.setIdacademicYear(materie.get(i).getDegreeCourse().getAcademicYear().getIdacademicYear());
			aa.setYear(materie.get(i).getDegreeCourse().getAcademicYear().getYear());
			
			DegreeCourseDTO degreeCourse = new DegreeCourseDTO();
			degreeCourse.setIdcourse(materie.get(i).getDegreeCourse().getIddegreeCourse());
			degreeCourse.setName(materie.get(i).getDegreeCourse().getTypeDegreeCourse().getName());
			degreeCourse.setAcademicYear(aa);
			
			AcademicYearDTO aaterm = new AcademicYearDTO();
			aaterm.setIdacademicYear(materie.get(i).getTerm().getAcademicYear().getIdacademicYear());
			aaterm.setYear(materie.get(i).getTerm().getAcademicYear().getYear());
			
			TermDTO term = new TermDTO();
			term.setIdterm(materie.get(i).getTerm().getIdterm());
			term.setAcademicYear(aaterm);
			
			subjectDTO.setId(materie.get(i).getIdsubject());
			subjectDTO.setName(materie.get(i).getTypeSubject().getName());
			subjectDTO.setTypeSubjectDTO(type);
			subjectDTO.setDegreecourseDTO(degreeCourse);
			subjectDTO.setTerm(term);
			
			previewChat.setSubject(subjectDTO);
			
			previewChatDTOs.add(previewChat);
			
		}
		
		List<Teacher> allTeachers = userRepository.searchTeachers(iduser, keyword);
		
		for(int i=0; i<allTeachers.size(); i++) {

			PreviewChatDTO previewChat = new PreviewChatDTO();
			
			UserDTO toUser = new UserDTO();
			toUser.setIduser(allTeachers.get(i).getUser().getIduser());
			toUser.setName(allTeachers.get(i).getUser().getName());
			toUser.setSurname(allTeachers.get(i).getUser().getSurname());
			
			try {
				toUser.setPropic(this.getUserImage(allTeachers.get(i).getUser().getFile().getIdfile()));
			} catch (NullPointerException e) {
			}
			
			previewChat.setToUser(toUser);
			
			previewChatDTOs.add(previewChat);
			
		}
		
		List<Student> allStudents = userRepository.searchStudents(iduser, keyword);
		
		for(int i=0; i<allStudents.size(); i++) {
			
			PreviewChatDTO previewChat = new PreviewChatDTO();

			UserDTO toUser = new UserDTO();
			toUser.setIduser(allStudents.get(i).getUser().getIduser());
			toUser.setName(allStudents.get(i).getUser().getName());
			toUser.setSurname(allStudents.get(i).getUser().getSurname());
			
			try {
				toUser.setPropic(this.getUserImage(allStudents.get(i).getUser().getFile().getIdfile()));
			} catch (NullPointerException e) {
			}
			previewChat.setToUser(toUser);

			previewChatDTOs.add(previewChat);
		
		}
		return previewChatDTOs;

	}
	
	@Transactional(readOnly=true)
	public List<PreviewChatDTO> getSubscribedCourses(int iduser) {
		List<PreviewChatDTO> previewChatDTOs = new ArrayList<PreviewChatDTO>();
		List<Subject> materie = new ArrayList<Subject>();
		if(userRepository.isTeacher(iduser)) {
			materie = subjectRepository.getCoursesByIdTeacher(iduser);
		} else {
			materie = subjectRepository.getCoursesByIdStudent(iduser);
		}
		for(int i=0; i<materie.size(); i++) {
			PreviewChatDTO previewChat = new PreviewChatDTO();
			
			SubjectDTO subjectDTO = new SubjectDTO();
			
			TypeSubjectDTO type = new TypeSubjectDTO();
			type.setIdtypeSubject(materie.get(i).getTypeSubject().getIdtypeSubject());
			type.setName(materie.get(i).getTypeSubject().getName());
			
			AcademicYearDTO aa = new AcademicYearDTO();
			aa.setIdacademicYear(materie.get(i).getDegreeCourse().getAcademicYear().getIdacademicYear());
			aa.setYear(materie.get(i).getDegreeCourse().getAcademicYear().getYear());
			
			DegreeCourseDTO degreeCourse = new DegreeCourseDTO();
			degreeCourse.setIdcourse(materie.get(i).getDegreeCourse().getIddegreeCourse());
			degreeCourse.setName(materie.get(i).getDegreeCourse().getTypeDegreeCourse().getName());
			degreeCourse.setAcademicYear(aa);
			
			AcademicYearDTO aaterm = new AcademicYearDTO();
			aaterm.setIdacademicYear(materie.get(i).getTerm().getAcademicYear().getIdacademicYear());
			aaterm.setYear(materie.get(i).getTerm().getAcademicYear().getYear());
			
			TermDTO term = new TermDTO();
			term.setIdterm(materie.get(i).getTerm().getIdterm());
			term.setAcademicYear(aaterm);
			
			subjectDTO.setId(materie.get(i).getIdsubject());
			subjectDTO.setName(materie.get(i).getTypeSubject().getName());
			subjectDTO.setTypeSubjectDTO(type);
			subjectDTO.setDegreecourseDTO(degreeCourse);
			subjectDTO.setTerm(term);
			
			previewChat.setSubject(subjectDTO);
			
			previewChatDTOs.add(previewChat);
			
		}
		return previewChatDTOs;

	}
	
	/*@Transactional(readOnly=true)
	public boolean isRead(int idmessage, int iduser) {
		return userReadsMessageRepository.isRead(iduser, idmessage);
	}
	
	
	
	@Transactional(readOnly=true)
	public void readAll(int idchat, int iduser){
		
		List<Message> messages = messageRepository.getNotReadOthersMessagesFromChatId(idchat, iduser);
		
		for(int i=0; i<messages.size(); i++) {
			UserReadsMessageId id = new UserReadsMessageId();
			id.setIdmessage(messages.get(i).getIdmessage());
			id.setIduser(iduser);
			
			UserReadsMessage read = new UserReadsMessage();
			read.setId(id);
			read.setDate(new Date());
			
			userReadsMessageRepository.save(read);
		}
		
	}*/
	
	@Transactional(readOnly=true)
	public String getPropic(int iduser) throws IOException{
		User user = userRepository.getOne(iduser);
		try {
			return this.getUserImage(user.getFile().getIdfile());
		} catch (Exception e) {
			return "null";
		}
	}
	
	@Transactional(readOnly=true)
	public UserDTO getProfileUser(int iduser) throws IOException{
		User user = userRepository.getOne(iduser);
		UserDTO userDTO = new UserDTO();
		userDTO.setIduser(user.getIduser());
		userDTO.setName(user.getName());
		userDTO.setSurname(user.getSurname());
		try {
			userDTO.setPropic(this.getUserImage(user.getFile().getIdfile()));
		} catch (Exception e) {
		}
		return userDTO;
	}
	
	public String getUserImage(int idfile) throws IOException {
		BufferedImage img = ImageIO.read(new File(location + idfile));             
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		baos.flush();
		Base64 base = new Base64(false);
		String encodedImage = base.encodeToString(baos.toByteArray());
		baos.close();
		return encodedImage = java.net.URLEncoder.encode(encodedImage, "ISO-8859-1");
	}
	
	/*@Transactional(readOnly=true)
	public List<PreviewChatDTO> search(String ssn, String keyword){
		List<PreviewChatDTO> previewChatDTOs = new ArrayList<PreviewChatDTO>();
		if(userRepository.isTeacher(ssn)) {
			List<DegreeCourse> courses = courseRepository.getCourseTeacher(ssn);
			
			for(int i=0; i<courses.size(); i++) {
				List<Teacher> colleaguesCourse = userRepository.searchTeacherColleagues(courses.get(i).getIddegreeCourse(), ssn, keyword);
				for(int j=0; j<colleaguesCourse.size(); j++) {
					PreviewChatDTO previewChatDTO = new PreviewChatDTO();
					PrivateChat privateChat = privateChatRepository.getPreviewsWithColleague(ssn, colleaguesCourse.get(j).getSsn());
					if(privateChat == null) {
						previewChatDTO.setId(colleaguesCourse.get(j).getSsn());
						previewChatDTO.setTitle(colleaguesCourse.get(j).getUser().getName() + " " + colleaguesCourse.get(j).getUser().getSurname());
						previewChatDTO.setPublic(false);
						previewChatDTOs.add(previewChatDTO);
					} else {
						if(!privateChat.getId().getUser1Ssn().equals(ssn)) {
							previewChatDTO.setId(privateChat.getId().getUser1Ssn());
							previewChatDTO.setTitle(privateChat.getUserByUser1Ssn().getName() + " " + privateChat.getUserByUser1Ssn().getSurname());
						} else {
							previewChatDTO.setId(privateChat.getId().getUser2Ssn());
							previewChatDTO.setTitle(privateChat.getUserByUser2Ssn().getName() + " " + privateChat.getUserByUser2Ssn().getSurname());
						}
						PrivateMessage privateMessage = privateMessageRepository.getLastMessage(privateChat.getId().getUser1Ssn(), privateChat.getId().getUser2Ssn());
						MessageDTO lastMessageDTO = new MessageDTO();
						lastMessageDTO.setId(privateMessage.getId().getIdprivateMessage());
						lastMessageDTO.setText(privateMessage.getText());
						lastMessageDTO.setDate(privateMessage.getDate());
						
						previewChatDTO.setLastMessage(lastMessageDTO);
						previewChatDTO.setPublic(false);
						previewChatDTOs.add(previewChatDTO);
					}
				}
				List<SubjectChat> enrollments = subjectChatRepository.search(ssn, keyword);
				
				for(int j=0; j<enrollments.size(); j++) {
					PreviewChatDTO previewChatDTO = new PreviewChatDTO();
					previewChatDTO.setId(String.valueOf(enrollments.get(j).getIdsubjectChat()));
					previewChatDTO.setTitle(enrollments.get(j).getSubject().getName());

					Message publicMessage = subjectMessageRepository.getLastMessage(enrollments.get(j).getIdsubjectChat());
					MessageDTO lastMessageDTO = new MessageDTO();
					lastMessageDTO.setId(publicMessage.getIdmessage());
					lastMessageDTO.setText(publicMessage.getText());
					lastMessageDTO.setDate(publicMessage.getDate());
					
					previewChatDTO.setLastMessage(lastMessageDTO);
					previewChatDTO.setPublic(true);
					previewChatDTOs.add(previewChatDTO);
				}
			}	
			
		} else if(userRepository.isStudent(ssn)) {
			List<DegreeCourse> courses = enrollmentRepository.getCourseStudent(ssn);
			
			for(int i=0; i<courses.size(); i++) {
				List<Student> colleagues = userRepository.searchStudentColleagues(courses.get(i).getIddegreeCourse(), ssn, keyword);
				for(int j=0; j<colleagues.size(); j++) {
					PreviewChatDTO previewChatDTO = new PreviewChatDTO();
					PrivateChat privateChat = privateChatRepository.getPreviewsWithColleague(ssn, colleagues.get(j).getUser().getSsn());
					if(privateChat == null) {
						previewChatDTO.setId(colleagues.get(j).getUser().getSsn());
						previewChatDTO.setTitle(colleagues.get(j).getUser().getName() + " " + colleagues.get(j).getUser().getSurname());
						previewChatDTO.setPublic(false);
						previewChatDTOs.add(previewChatDTO);
					} else {
						if(!privateChat.getId().getUser1Ssn().equals(ssn)) {
							previewChatDTO.setId(privateChat.getId().getUser1Ssn());
							previewChatDTO.setTitle(privateChat.getUserByUser1Ssn().getName() + " " + privateChat.getUserByUser1Ssn().getSurname());
						} else {
							previewChatDTO.setId(privateChat.getId().getUser2Ssn());
							previewChatDTO.setTitle(privateChat.getUserByUser2Ssn().getName() + " " + privateChat.getUserByUser2Ssn().getSurname());
						}
						PrivateMessage privateMessage = privateMessageRepository.getLastMessage(privateChat.getId().getUser1Ssn(), privateChat.getId().getUser2Ssn());
						MessageDTO lastMessageDTO = new MessageDTO();
						lastMessageDTO.setId(privateMessage.getId().getIdprivateMessage());
						lastMessageDTO.setText(privateMessage.getText());
						lastMessageDTO.setDate(privateMessage.getDate());
						
						previewChatDTO.setLastMessage(lastMessageDTO);
						previewChatDTO.setPublic(false);
						previewChatDTOs.add(previewChatDTO);
					}
				}
				List<SubjectChatHasStudent> enrollments = enrollmentSubjectChatRepository.search(ssn, keyword);
				
				for(int j=0; j<enrollments.size(); j++) {
					PreviewChatDTO previewChatDTO = new PreviewChatDTO();
					previewChatDTO.setId(String.valueOf(enrollments.get(j).getSubjectChat().getIdsubjectChat()));
					previewChatDTO.setTitle(enrollments.get(j).getSubjectChat().getSubject().getName());

					Message publicMessage = subjectMessageRepository.getLastMessage(enrollments.get(j).getSubjectChat().getIdsubjectChat());
					MessageDTO lastMessageDTO = new MessageDTO();
					lastMessageDTO.setId(publicMessage.getIdmessage());
					lastMessageDTO.setText(publicMessage.getText());
					lastMessageDTO.setDate(publicMessage.getDate());
					
					previewChatDTO.setLastMessage(lastMessageDTO);
					previewChatDTO.setPublic(true);
					previewChatDTOs.add(previewChatDTO);
				}
			}
		}
		Collections.sort(previewChatDTOs, new Comparator<PreviewChatDTO>() {
            @Override
            public int compare(PreviewChatDTO o1, PreviewChatDTO o2) {
                return o2.getLastMessage().getDate().compareTo(o1.getLastMessage().getDate());
            }
        });
		return previewChatDTOs;
		
	}

	
	@Transactional
	public ChatDTO getSubjectChat(String id) throws ChatNotFoundException {
		try {
			SubjectChat chat = subjectChatRepository.findById(Integer.parseInt(id)).get();
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(chat.getSubject().getIdsubject());
			subjectDTO.setName(chat.getSubject().getName());
			
			List<Message> messages = subjectMessageRepository.getSubjectMessages(chat.getSubject().getIdsubject());
			List<MessageDTO> messageDTOs = new ArrayList<MessageDTO>();
			
			for(int i=0; i<messages.size(); i++) {
				UserDTO sender = new UserDTO();
				sender.setSsn(messages.get(i).getUser().getSsn());
				sender.setName(messages.get(i).getUser().getName());
				sender.setSurname(messages.get(i).getUser().getSurname());
				
				MessageDTO messageDTO = new MessageDTO();
				messageDTO.setId(messages.get(i).getIdmessage());
				messageDTO.setText(messages.get(i).getText());
				messageDTO.setDate(messages.get(i).getDate());
				messageDTO.setSender(sender);
				messageDTOs.add(messageDTO);
			}
			
			ChatDTO chatDTO = new ChatDTO();
			chatDTO.setIdChat(String.valueOf(chat.getIdsubjectChat()));
			chatDTO.setTitle(chat.getSubject().getName());
			chatDTO.setSubject(subjectDTO);
			chatDTO.setPublic(true);
			return chatDTO;
		} catch (Exception e) {
			throw new ChatNotFoundException();
		}
	}
	
	@Transactional
	public ChatDTO getPrivateChat(String ssn1, String ssn2) {
		PrivateChat chat = privateChatRepository.getPreviewsWithColleague(ssn1, ssn2);
		ChatDTO chatDTO = new ChatDTO();
		if(chat.getUserByUser1Ssn().getSsn().equals(ssn1)) {
			UserDTO recipient = new UserDTO();
			recipient.setSsn(chat.getUserByUser2Ssn().getSsn());
			recipient.setName(chat.getUserByUser2Ssn().getName());
			recipient.setSurname(chat.getUserByUser2Ssn().getSurname());
			
			chatDTO.setTitle(recipient.getName() + " " + recipient.getSurname());
			chatDTO.setRecipient(recipient);
			chatDTO.setIdChat(ssn2);
		} else {
			UserDTO recipient = new UserDTO();
			recipient.setSsn(chat.getUserByUser1Ssn().getSsn());
			recipient.setName(chat.getUserByUser1Ssn().getName());
			recipient.setSurname(chat.getUserByUser1Ssn().getSurname());
			
			chatDTO.setIdChat(ssn1);
			chatDTO.setTitle(recipient.getName() + " " + recipient.getSurname());
			chatDTO.setRecipient(recipient);
		}
		
		List<PrivateMessage> messages = privateMessageRepository.getChat(ssn1, ssn2);
		List<MessageDTO> messageDTOs = new ArrayList<MessageDTO>();
		for(int i=0; i<messages.size(); i++) {
			UserDTO sender = new UserDTO();
			sender.setSsn(messages.get(i).getUser().getSsn());
			sender.setName(messages.get(i).getUser().getName());
			sender.setSurname(messages.get(i).getUser().getSurname());
			
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setId(messages.get(i).getId().getIdprivateMessage());
			messageDTO.setText(messages.get(i).getText());
			messageDTO.setDate(messages.get(i).getDate());
			messageDTO.setSender(sender);
			messageDTOs.add(messageDTO);
		}
		chatDTO.setAllMessages(messageDTOs);
		chatDTO.setPublic(false);
		return chatDTO;
	}*/
}
