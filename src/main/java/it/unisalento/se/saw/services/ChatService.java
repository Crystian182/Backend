package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IChatService;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Message;
import it.unisalento.se.saw.domain.PrivateChat;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.dto.ChatDTO;
import it.unisalento.se.saw.dto.MessageDTO;
import it.unisalento.se.saw.dto.PreviewChatDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;
import it.unisalento.se.saw.repositories.DegreeCourseRepository;
import it.unisalento.se.saw.repositories.EnrollmentRepository;
import it.unisalento.se.saw.repositories.PrivateChatRepository;
import it.unisalento.se.saw.repositories.PrivateMessageRepository;
import it.unisalento.se.saw.repositories.SubjectChatHasStudentRepository;
import it.unisalento.se.saw.repositories.SubjectChatRepository;
import it.unisalento.se.saw.repositories.SubjectMessageRepository;
import it.unisalento.se.saw.repositories.SubjectRepository;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class ChatService implements IChatService {
	
	@Autowired
	PrivateMessageRepository privateMessageRepository;
	
	@Autowired
	SubjectMessageRepository subjectMessageRepository;
	
	@Autowired
	PrivateChatRepository privateChatRepository;
	
	@Autowired
	SubjectChatRepository subjectChatRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	DegreeCourseRepository courseRepository;
	
	@Autowired
	SubjectChatHasStudentRepository enrollmentSubjectChatRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	/*@Transactional(readOnly=true)
	public List<PreviewChatDTO> getAllPreviews(int iduser){
		List<PreviewChatDTO> previewChatDTOs = new ArrayList<PreviewChatDTO>();
		if(userRepository.isTeacher(iduser)) {
			List<DegreeCourse> courses = courseRepository.getCourseTeacher(iduser);
			
			for(int i=0; i<courses.size(); i++) {
				List<Teacher> colleaguesCourse = userRepository.getTeacherColleagues(courses.get(i).getIddegreeCourse(), iduser);
				for(int j=0; j<colleaguesCourse.size(); j++) {
					PreviewChatDTO previewChatDTO = new PreviewChatDTO();
					PrivateChat privateChat = privateChatRepository.getPreviewsWithColleague(iduser, colleaguesCourse.get(j).getIduser());
					if(privateChat == null) {
						previewChatDTO.setId(colleaguesCourse.get(j).getIduser());
						previewChatDTO.setTitle(colleaguesCourse.get(j).getUser().getName() + " " + colleaguesCourse.get(j).getUser().getSurname());
						previewChatDTO.setPublic(false);
						previewChatDTOs.add(previewChatDTO);
					} else {
						if(!privateChat.getUserByUser1Iduser().equals(iduser)) {
							previewChatDTO.set(privateChat.getUserByUser1Iduser());
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
				List<SubjectChat> enrollments = subjectChatRepository.getTeacherPreviews(ssn);
				
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
				List<Student> colleagues = userRepository.getStudentColleagues(courses.get(i).getIddegreeCourse(), ssn);
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
				List<SubjectChatHasStudent> enrollments = enrollmentSubjectChatRepository.getPreviews(ssn);
				
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
	
	@Transactional(readOnly=true)
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
