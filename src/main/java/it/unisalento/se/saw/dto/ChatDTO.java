package it.unisalento.se.saw.dto;

import java.util.List;

public class ChatDTO {
	
	private int idChat;
	private String title;
	private MessageDTO lastMessage;
	private List<MessageDTO> allMessages;
	private SubjectOfStudyDTO subjectOfStudy;
	private UserDTO user;
	private boolean isPublic;
	private boolean isSubscribed;
	
	public int getIdChat() {
		return idChat;
	}
	public void setIdChat(int idChat) {
		this.idChat = idChat;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public MessageDTO getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(MessageDTO lastMessage) {
		this.lastMessage = lastMessage;
	}
	public List<MessageDTO> getAllMessages() {
		return allMessages;
	}
	public void setAllMessages(List<MessageDTO> allMessages) {
		this.allMessages = allMessages;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public SubjectOfStudyDTO getSubjectOfStudy() {
		return subjectOfStudy;
	}
	public void setSubjectOfStudy(SubjectOfStudyDTO subjectOfStudy) {
		this.subjectOfStudy = subjectOfStudy;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public boolean isSubscribed() {
		return isSubscribed;
	}
	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	
	

}
