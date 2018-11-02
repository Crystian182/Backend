package it.unisalento.se.saw.dto;

import java.util.List;

public class PreviewChatDTO {
	
	private String id;
	private String title;
	private MessageDTO lastMessage;
	private boolean isPublic;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	

}
