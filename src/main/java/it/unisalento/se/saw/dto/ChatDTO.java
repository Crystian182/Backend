package it.unisalento.se.saw.dto;

import java.util.List;

public class ChatDTO {
	
	private String idChat;
	private String title;
	private List<MessageDTO> allMessages;
<<<<<<< HEAD
=======
	private SubjectDTO subject;
	private UserDTO user;
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098
	private boolean isPublic;
	private SubjectDTO subject;
	private UserDTO recipient;
	
	public String getIdChat() {
		return idChat;
	}
	public void setIdChat(String idChat) {
		this.idChat = idChat;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<MessageDTO> getAllMessages() {
		return allMessages;
	}
	public void setAllMessages(List<MessageDTO> allMessages) {
		this.allMessages = allMessages;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
	public UserDTO getRecipient() {
		return recipient;
	}
	public void setRecipient(UserDTO recipient) {
		this.recipient = recipient;
	}
	
	

}
